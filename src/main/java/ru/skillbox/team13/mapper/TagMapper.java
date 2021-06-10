package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.TagDTO;
import ru.skillbox.team13.entity.Tag;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TagMapper {
    public static TagDTO convertTagToTagDTO(Tag tag)    {
        return TagDTO.builder().tag(tag.getTag()).build();
    }

    public static Set<TagDTO> convertSetTagToSetTagDTO(Set<Tag> tags)  {
        return tags.stream()
                .map(TagMapper::convertTagToTagDTO)
                .collect(Collectors.toSet());
    }
}
