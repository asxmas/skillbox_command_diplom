package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.DialogDto;
import ru.skillbox.team13.dto.DialogMessageDto;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Message;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.MessageReadStatus;
import ru.skillbox.team13.mapper.DialogMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.Dialog2PersonRepository;
import ru.skillbox.team13.repository.DialogRepository;
import ru.skillbox.team13.repository.MessageRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.service.DialogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final Dialog2PersonRepository dialog2PersonRepository;
    private final DialogRepository dialogRepository;
    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;
    private final UserServiceImpl userService;

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getPersonDialogs(String query, int offset, int itemPerPage) {

        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Page<Dialog2Person> dialogPage = dialog2PersonRepository.findPersonDialogs(pageable, currentPerson.getId());
        List<DialogDto> results = dialogPage.stream().map(
                dialog2Person -> {
                    Person recipientPerson = recipientPerson(currentPerson, dialog2Person.getDialog());
                return DialogMapper.convertDialog2PersonToDialogDTO(dialog2Person, recipientPerson);
                }).collect(Collectors.toList());
        return WrapperMapper.wrap(results, (int) dialogPage.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper createDialog(ArrayList<Integer> userIds) {

        Dialog dialog = new Dialog();
        int dialogId = dialogRepository.saveAndFlush(dialog).getId();
        //добавление текущего пользователя в диалог
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        if (!userIds.contains(currentPersonId)) userIds.add(currentPersonId);
        List<Person> personList = personRepository.findAllById(userIds);
        List<Dialog2Person> dialog2PersonList = personList.stream().map(person -> new Dialog2Person()
                .setPerson(person)
                .setDialog(dialog)).collect(Collectors.toList());
        dialog2PersonRepository.saveAll(dialog2PersonList);
        return WrapperMapper.wrap(Map.of("id", dialogId), true);
    }

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getUnreadCount() {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        Integer countUnread = dialog2PersonRepository.countAllUnread(currentPersonId);
        //при отсутствии диалогов у пользователя вернется null, нет непрочитанных сообщений
        if (countUnread == null) countUnread = 0;
        return WrapperMapper.wrap(Map.of("count", countUnread), true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper deleteDialog(int dialogId) {

        //удаляем перекрестную ссылку на Message (поле lastMessage) в диалоге для устранения ограничений внешнего ключа
        Dialog dialog = dialogRepository.getById(dialogId);
        dialog.setLastMessage(null);
        dialogRepository.saveAndFlush(dialog);

        dialog2PersonRepository.deleteAllByDialog(dialog.getId());

        dialogRepository.deleteById(dialogId);
        return WrapperMapper.wrap(Map.of("id", dialogId), true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper sendMessage(int dialogId, String messageText) {

        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Dialog dialog = dialogRepository.findById(dialogId).get();
        Person dstPerson = recipientPerson(currentPerson, dialog);
        Message message = createMessage(dialog, currentPerson, dstPerson, messageText);
        dialog.setLastMessage(message);
        messageRepository.save(message);
        dialogRepository.save(dialog);
        setUnread(dialog, currentPerson);
        //todo сообщение должно попасть в нотификешен
        return WrapperMapper.wrap(DialogMapper.convertMessageToDialogMessageDTO(message), true);
    }

    @Override
    @Transactional
    public DTOWrapper getDialogMessages(int dialogId, int fromMessageId, int offset, int itemPerPage) {

        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage, Sort.by("id").descending());
        Dialog dialog = dialogRepository.getById(dialogId);
        if (fromMessageId == 0) {
            try {
                fromMessageId = messageRepository.findFirstByOrderByIdDesc().getId();
            }
            catch (NullPointerException e){
                //в новом диалоге нет сообщений
                return WrapperMapper.wrap(Collections.emptyList(), 0, offset, itemPerPage, true);
            }
        }
        Page<Message> messagePage = messageRepository.findByDialog(pageable, fromMessageId, dialogId);
        Person currentPerson = userService.getAuthorizedUser().getPerson();
        List<DialogMessageDto> results = messagePage.stream()
            .map(message -> DialogMapper.convertMessageToDialogMessageDTO(message, currentPerson.getId() == message.getAuthor().getId()))
            .collect(Collectors.toList());
        //все выданные фронту сообщения считаю прочтенными, если их выдали получателю, необходима доработка фронта
        messageRepository.setStatusForGroup(MessageReadStatus.READ, currentPerson.getId(), messagePage.stream().map(m -> m.getId()).collect(Collectors.toList()));
        setUnread(dialog, recipientPerson(currentPerson, dialog));
        return WrapperMapper.wrap(results, (int) messagePage.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper addUsersToDialog(int dialogId, ArrayList<Integer> userIds) {

        //проверка на присутствие пользователей в диалоге
        List<Dialog2Person> personsInDialog = dialog2PersonRepository.findAllByDialogIdAndPersonIdIsIn(dialogId, userIds);
        if (!personsInDialog.isEmpty()) { throw new BadRequestException("Пользователь участвует в диалоге"); }
        List<Person> personList = personRepository.findAllById(userIds);
        Dialog dialog = dialogRepository.getById(dialogId);
        List<Dialog2Person> dialog2PersonList = personList.stream().map(person -> new Dialog2Person()
                .setPerson(person)
                .setDialog(dialog)
                //при добавлении пользователя в диалог устанавливаем непрочитанными - все сообщения
                .setUnreadCount(dialog.getMessages().size())).collect(Collectors.toList()) ;
        dialog2PersonRepository.saveAll(dialog2PersonList);
        return WrapperMapper.wrap(Map.of("user_ids", userIds), true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper deleteUserFromDialog(int dialogId, ArrayList<Integer> userIds) {
        if (!dialogRepository.existsById(dialogId)) { throw new BadRequestException("Диалога не существует");}
        dialog2PersonRepository.deleteAllByDialogIdAndPersonIdIsIn(dialogId, userIds);
        //todo если в диалоге не осталось собеседников, то... выяснить логику: почистить вместе с сообщениями?
        return WrapperMapper.wrap(Map.of("user_ids", userIds), true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper getInviteLink(int dialogId) {

        Dialog dialog = dialogRepository.findById(dialogId).get();
        //сли у диалога уже есть ссылка-приглашение, то возвращаем её
        String inviteLink = dialog.getInviteLink();
        //если нет, то создаем и сохраняем новую
        if (dialog.getInviteLink() == null) {
            inviteLink = UUID.randomUUID().toString();
            dialog.setInviteLink(inviteLink);
            dialogRepository.save(dialog);
        }
        return WrapperMapper.wrap(Map.of("link", inviteLink) ,true);
    }

    @Override
    @Transactional
    public DTOWrapper addUserToDialogByLink(int dialogId, String inviteLink) {

        Dialog dialog = dialogRepository.findFirstByInviteLink(inviteLink);
        Person person = userService.getAuthorizedUser().getPerson();
        //todo нужна или нет проверка на то что пользователь входит по своему же приглашению...
        Dialog2Person dialog2Person = new Dialog2Person()
                .setDialog(dialog)
                .setPerson(person)
                .setUnreadCount(dialog.getMessages().size());
        dialog2PersonRepository.save(dialog2Person);
        return WrapperMapper.wrap(Map.of("user_ids", List.of(person.getId())), true);
    }

    private void setUnread(Dialog dialog, Person currentPerson){

        Dialog2Person dialog2Person = dialog2PersonRepository.findByDialogAndPerson(dialog, recipientPerson(currentPerson, dialog));
        dialog2Person.setUnreadCount(messageRepository.countMessageByReadStatusEqualsAndDialogAndAuthor(
                                                        MessageReadStatus.SENT,
                                                        dialog,
                                                        currentPerson));
        dialog2PersonRepository.save(dialog2Person);
    }


    private Message createMessage(Dialog dialog, Person srcPerson, Person dstPerson, String messageText){

        return new Message()
                .setTime(LocalDateTime.now())
                .setAuthor(srcPerson)
                .setRecipient(dstPerson)
                .setMessageText(messageText)
                .setReadStatus(MessageReadStatus.SENT)
                .setDialog(dialog);
    }

    private Person recipientPerson(Person person, Dialog dialog) {

        return dialog2PersonRepository.findDialog2PersonByDialog(dialog)
                .stream()
                .filter(d2p -> d2p.getPerson().getId() != person.getId())
                .findAny().get()
                .getPerson();
    }

    @Override
    public DTOWrapper setStatus(int msgId, MessageReadStatus status) {
        messageRepository.setStatusForId(status, msgId);
        log.debug("Setting status {} for message id {}", status.name(), msgId);
        return WrapperMapper.wrap(Map.of("message", "ok"), true);
    }
}