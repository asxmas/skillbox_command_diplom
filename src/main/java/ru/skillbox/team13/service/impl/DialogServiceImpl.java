package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
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
import ru.skillbox.team13.exception.BadRequestException;
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
        Page<Dialog2Person> dialogPage = dialog2PersonRepository.findPersonDialogs(pageable, query, currentPerson.getId());
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

        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        Dialog dialog = new Dialog();
        int dialogId = dialogRepository.saveAndFlush(dialog).getId();
        //добавление текущего пользователя в диалог
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

        //todo проверить по фронту и скорее всего убрать необходимость хранения получателя сообщения
        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Dialog dialog = dialogRepository.findById(dialogId).get();
        Person dstPerson = recipientPerson(currentPerson, dialog);
        Message message = createMessage(dialog, currentPerson, dstPerson, messageText);
        dialog.setLastMessage(message);
        messageRepository.save(message);
        dialogRepository.save(dialog);
        incrementUnread(dialog, currentPerson);
        //todo сообщение должно попасть в нотификешен
        return WrapperMapper.wrap(DialogMapper.convertMessageToDialogMessageDTO(message), true);
    }

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getDialogMessages(int dialogId, int fromMessageId, int offset, int itemPerPage) {

        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage, Sort.by("id").descending());
        if (fromMessageId == 0) { fromMessageId = messageRepository.findFirstByOrderByIdDesc().getId(); }
        Page<Message> messagePage = messageRepository.findByDialog(pageable, fromMessageId, dialogId);
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<DialogMessageDto> results = messagePage.stream()
            .map(message -> DialogMapper.convertMessageToDialogMessageDTO(message, currentPersonId == message.getAuthor().getId()))
            .collect(Collectors.toList());
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

    private void incrementUnread(Dialog dialog, Person currentPerson){

        List<Dialog2Person> dialog2Person = dialog2PersonRepository.findDialog2PersonByDialog(dialog);
        dialog2PersonRepository.saveAll(
            dialog2Person.stream()
                         .filter(d2p -> d2p.getPerson() != currentPerson)
                         .map(d2p -> {
                                d2p.setUnreadCount(d2p.getUnreadCount() + 1);
                                return d2p;})
                         .collect(Collectors.toList()));
    }

    private Message createMessage(Dialog dialog, Person srcPerson, Person dstPerson, String messageText){
    //todo проверить - фронту в сообщении не нужен получатель, упростить метод - убрать dstPerson
        ///
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
}