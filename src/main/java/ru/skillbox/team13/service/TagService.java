package ru.skillbox.team13.service;

import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.TagDto;

import java.util.Optional;

public interface TagService {
    DTOWrapper getTags(String tag, int offset, int itemPerPage);

    TagDto addTag(TagDto tagDto);

    Optional<String> deleteTag(int id);
}
