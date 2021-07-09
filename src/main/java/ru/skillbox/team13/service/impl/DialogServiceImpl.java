package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Person person = userService.getAuthorizedUser().getPerson();
        Page<Dialog2Person> dialogPage = dialog2PersonRepository.findPersonDialogs(pageable, query, person.getId());
        List<DialogDto> results = dialogPage.stream().map(DialogMapper::convertDialog2PersonToDialogDTO).collect(Collectors.toList());
        return WrapperMapper.wrap(results, (int) dialogPage.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper createDialog(ArrayList<Integer> userIds) {

        int currentUserId = userService.getAuthorizedUser().getPerson().getId();
        Dialog dialog = new Dialog();
        int dialogId = dialogRepository.saveAndFlush(dialog).getId();
        //добавление текущего пользователя в диалог
        if (!userIds.contains(currentUserId)) userIds.add(currentUserId);
        List<Person> personList = personRepository.findAllById(userIds);
        List<Dialog2Person> dialog2PersonList = personList.stream().map(person -> new Dialog2Person()
                .setPerson(person)
                .setDialog(dialog)).collect(Collectors.toList());
        dialog2PersonRepository.saveAllAndFlush(dialog2PersonList);
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

        dialog2PersonRepository.deleteByDialog(dialog);

        dialogRepository.deleteById(dialogId);
        return WrapperMapper.wrap(Map.of("id", dialogId), true);
    }

    @Override
    @Transactional
    @Modifying
    public DTOWrapper sendMessage(int dialogId, String messageText) {

        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Dialog dialog = dialogRepository.findById(dialogId).get();
        Message message = createMessage(dialog, currentPerson, messageText);
        dialog.setLastMessage(message);
        messageRepository.save(message);
        dialogRepository.save(dialog);
        incrementUnread(dialog, currentPerson);
        //todo сообщение должно попасть в нотификешен
        return WrapperMapper.wrap(DialogMapper.convertMessageToDialogMessageDTO(message), true);
    }

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getDialogMessages(int dialogId, String query, int offset, int itemPerPage) {

        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Page<Message> messagePage = messageRepository.findByDialog(pageable, query, dialogId);
        List<DialogMessageDto> results = messagePage.stream().map(DialogMapper::convertMessageToDialogMessageDTO).collect(Collectors.toList());
        return WrapperMapper.wrap(results, (int) messagePage.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper setStatus(int msgId, MessageReadStatus status) {
        messageRepository.setStatusForId(status, msgId);
        log.debug("Setting status {} for message id {}", status.name(), msgId);
        return WrapperMapper.wrap(Map.of("message", "ok"), true);
    }

    @Override
    public DTOWrapper setUserDialogStatus(int dialogId, int userId) { //todo implement
        return null;
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

    private Message createMessage(Dialog dialog, Person currentPerson, String messageText){

        return new Message()
                .setTime(LocalDateTime.now())
                .setAuthor(currentPerson)
                .setRecipient(currentPerson)
                .setMessageText(messageText)
                .setReadStatus(MessageReadStatus.SENT)
                .setDialog(dialog);
    }
}