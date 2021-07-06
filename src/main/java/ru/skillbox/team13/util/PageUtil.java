package ru.skillbox.team13.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable getPageable(int offset, int itemPerPage) {
        int page = offset / itemPerPage;
        return PageRequest.of(page, itemPerPage);
    }
}