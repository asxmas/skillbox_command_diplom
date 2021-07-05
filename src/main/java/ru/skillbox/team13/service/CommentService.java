package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.dto.DTOWrapper;

public interface CommentService {

    DTOWrapper getAllCommentByPost(int id, int offset, int itemPerPage);

    DTOWrapper setCommentToPost(CommentToPostDto commentToPostDto, int id);

    DTOWrapper editCommentById();

    DTOWrapper deleteCommentById();

    DTOWrapper restoreCommentById();

    DTOWrapper reportToPost();

    DTOWrapper reportToComment();




}
