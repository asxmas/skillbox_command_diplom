package ru.skillbox.team13.util;

import ru.skillbox.team13.dto.CommentDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

public class CommentUtil {

    public static List<CommentDto> twoLevelCommentSort(Collection<CommentDto> comments) {
        //first level - comments with null 'parentId' field
        //second level - comments with non-null 'parentId' field
        //comments with 'parentId' pointing on non-first level comment will be lost!

        Map<Boolean, List<CommentDto>> map = comments.stream()
                .collect(partitioningBy(c -> isNull(c.getParentId())));

        List<CommentDto> firstLevel = map.get(true);
        List<CommentDto> secondLevel = map.get(false);

        //if initial array doesn't contain any parents, return all ordered
        if (firstLevel.isEmpty()) {
            return secondLevel;
        }

        //group by parent
        Map<Integer, List<CommentDto>> parentChildren = secondLevel.stream().collect(groupingBy(CommentDto::getParentId));


        for (CommentDto parent : firstLevel) {
            if (parentChildren.containsKey(parent.getId())) {
                parent.setComments(parentChildren.get(parent.getId()));
            }
        }

        return firstLevel;
    }
}
