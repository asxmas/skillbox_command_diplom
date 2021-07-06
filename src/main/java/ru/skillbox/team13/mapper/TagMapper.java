package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.entity.Tag;

public class TagMapper {
    public static TagDto mapToTagDto(Tag entity){
        TagDto tagDto = new TagDto();
        tagDto.setId(entity.getId());
        tagDto.setTag(entity.getTag());
        return tagDto;
    }
}
