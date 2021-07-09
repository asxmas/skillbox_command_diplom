package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.entity.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public class TagMapper {
    public static TagDto mapToTagDto(Tag entity){
        TagDto tagDto = new TagDto();
        tagDto.setId(entity.getId());
        tagDto.setTag(entity.getTag());
        return tagDto;
    }

    public static Set<TagDto> convertSetTagToSetTagDTO(Set<Tag> tags)  {
        return tags.stream()
                .map(TagMapper::mapToTagDto)
                .collect(Collectors.toSet());
    }

}
