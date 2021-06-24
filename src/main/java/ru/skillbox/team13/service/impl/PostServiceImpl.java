package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements ru.skillbox.team13.service.PostService {

    @Override
    public DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public DTOWrapper getById(int id) {
        return null;
    }

    @Override
    public DTOWrapper edit(int id, Long pubDate, String title, String text) {
        return null;
    }

    @Override
    public DTOWrapper deleteById(int id) {
        return null;
    }

    @Override
    public DTOWrapper recoverById(int id) {
        return null;
    }
}
