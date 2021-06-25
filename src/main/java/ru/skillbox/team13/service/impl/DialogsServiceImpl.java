package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.DialogDto;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.DialogMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.Dialog2PersonRepository;
import ru.skillbox.team13.service.DialogsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DialogsServiceImpl implements DialogsService {

    private final Dialog2PersonRepository dialog2PersonRepository;
    private final UserServiceImpl userService;

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getUserDialogs(String query, int offset, int itemPerPage) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Person person = userService.getAuthorizedUser().getPerson();
        Page<Dialog> dialogsPage= dialog2PersonRepository.findUserDialogs(pageable, query, person);
        if (dialogsPage == null) { throw new BadRequestException("User " + person.getEmail() + " have no dialogs");}
        else {
            List<DialogDto> results = dialogsPage.stream().map(DialogMapper::convertDialogToDialogDTO).collect(Collectors.toList());
            return WrapperMapper.wrap(results, (int) dialogsPage.getTotalElements(), offset, itemPerPage, true);
        }
    }

    @Override
    public DTOWrapper createDialog(int[] userIds) { return null; }

    @Override
    public DTOWrapper getUnreadCount() {
        return null;
    }

    @Override
    public DTOWrapper deleteDialog(int dialogId) {
        return null;
    }
}
