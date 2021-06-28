package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.NotificationDto;
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
    public DTOWrapper getAllNotification(int offset, int limit) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<Notification> noteList = noteRepository.findAllByPersonId(currentPersonId);
        return NotificationMapper.mapNotifications(noteList, offset, limit);
    }

    @Override
    public DTOWrapper getNotificationById(int offset, int limit, int id, boolean isAllRead) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        if(id < 0 || isAllRead) {
            DTOWrapper request = getAllNotification(offset, limit);
            noteRepository.deleteAllByPersonId(currentPersonId);
            return request;
        }
        NotificationDto noteDto = NotificationMapper.mapNotification(noteRepository.findNotificationByPersonIdAndAndId(currentPersonId, id));
        NotificationDto[] noteArray = {noteDto};
        noteRepository.deleteByPersonIdAndId(currentPersonId, id);
        return WrapperMapper.wrap(noteArray, 1, offset, limit, true);
    }
}
