package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

import java.util.List;

public interface PostService {
    DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage);

    DTOWrapper getFeed(String name, int offset, int itemPerPage);

    DTOWrapper getById(int id);

    DTOWrapper edit(int id, Long pubDate, String title, List<String> tagNames, String text);

    DTOWrapper deleteById(int id);

    DTOWrapper recoverById(int id);

    DTOWrapper getWallForUserId(int id, int offset, int itemPerPage);

    DTOWrapper post(String title, String text, List<String> tags, Integer id, Long pubDate);

    DTOWrapper deletePostsForAuthor(int authorId);
}
