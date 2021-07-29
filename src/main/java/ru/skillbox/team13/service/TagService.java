package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TagService {
    DTOWrapper getTags(String tag, int offset, int itemPerPage);

    TagDto addTag(TagDto tagDto);

    Optional<String> deleteTag(int id);

    Set<Tag> getTagsByName(Set<String> tagNames);

    Map<Integer, Set<String>> getPostIdTagsMap(List<Integer> postIds);
}
