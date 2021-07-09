package ru.skillbox.team13.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LongpollParamDto;
import ru.skillbox.team13.service.LongpollService;

@Service
public class LongpollServiceImpl implements LongpollService {
    @Override
    public DTOWrapper getConnectionData() {
        return null;
    }

    @Override
    public DTOWrapper getSomething(LongpollParamDto paramDto) {
        return null;
    }
}
