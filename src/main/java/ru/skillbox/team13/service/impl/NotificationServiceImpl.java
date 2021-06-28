package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.NotificationService;
import ru.skillbox.team13.service.UserService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;

    @Override
    public DTOWrapper getNotification() {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();


        return null;
    }
}
