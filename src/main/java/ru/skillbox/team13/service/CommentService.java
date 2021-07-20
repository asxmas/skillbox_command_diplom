package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface CommentService {
    DTOWrapper getComments(int id, int offset, int itemPerPage);

    DTOWrapper postComment(int id, Integer parentId, String text);

    DTOWrapper editComment(int commentId, String commentText);

    DTOWrapper delete(int commentId);

    DTOWrapper restore(int commentId);
}
