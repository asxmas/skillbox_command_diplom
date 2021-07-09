package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LongpollParamDto;

public interface LongpollService {
    DTOWrapper getConnectionData();

    DTOWrapper getSomething(LongpollParamDto paramDto);
}
