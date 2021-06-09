package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.TagDTO;
import ru.skillbox.team13.entity.Tag;

@RequiredArgsConstructor
public class TagMapper {
    public static TagDTO convertTagToTagDTO(Tag tag)    {
        return TagDTO.builder().tag(tag.getTag()).build();
    }
}
