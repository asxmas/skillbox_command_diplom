package ru.skillbox.team13.util;

import ru.skillbox.team13.dto.CommentDTO;
import ru.skillbox.team13.dto.TagDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.mapper.CommentMapper;
import ru.skillbox.team13.mapper.TagMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetUtil {
    public static Set<CommentDTO> convertSetCommentToSetCommentDTO(Set<Comment> comments)  {
        Set<CommentDTO> commentDTOSet = new HashSet<>();
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext())  {
            commentDTOSet.add(CommentMapper.convertCommentToCommentDTO(iterator.next()));
        }
        return commentDTOSet;
    }

    public static Set<TagDTO> convertSetTagToSetTagDTO(Set<Tag> comments)  {
        Set<TagDTO> commentDTOSet = new HashSet<>();
        Iterator<Tag> iterator = comments.iterator();
        while (iterator.hasNext())  {
            commentDTOSet.add(TagMapper.convertTagToTagDTO(iterator.next()));
        }
        return commentDTOSet;
    }
}
