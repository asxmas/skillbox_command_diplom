package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface FeedsService {
    DTOWrapper serve(String searchSubstring, int offset, int itemPerPage);
}
