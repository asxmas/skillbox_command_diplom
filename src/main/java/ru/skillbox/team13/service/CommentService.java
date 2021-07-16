package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface CommentService {
    DTOWrapper getComments(int id, int offset, int itemPerPage);

    DTOWrapper postTopLevelComment(int id, String text);

    DTOWrapper postCommentToComment(int id, Integer parentId, String text);
}
