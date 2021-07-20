package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.NotificationDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Notification;
import ru.skillbox.team13.mapper.NotificationMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.NotificationBaseRepository;
import ru.skillbox.team13.repository.NotificationRepository;
import ru.skillbox.team13.service.NotificationService;
import ru.skillbox.team13.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;
    private final NotificationBaseRepository noteBaseRepository;
    private final NotificationRepository noteRepository;

    @Override
    @Transactional
    public DTOWrapper getAllNotification() {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<Notification> noteList = noteRepository.findAllByPersonId(currentPersonId);
        return NotificationMapper.mapNotifications(noteList, 0,10);
    }

    @Override
    @Transactional
    public DTOWrapper getNotificationById(int id, boolean isAllRead) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        if(id < 0 || isAllRead) {
            DTOWrapper request = getAllNotification();
            noteRepository.deleteAllByPersonId(currentPersonId);
            return request;
        }
        NotificationDto noteDto = NotificationMapper.mapNotification(noteRepository.findNotificationByPersonIdAndAndId(currentPersonId, id));
        NotificationDto[] noteArray = {noteDto};
        noteRepository.deleteByPersonIdAndId(currentPersonId, id);
        return WrapperMapper.wrap(noteArray, 1, 0, 10, true);
    }


}
