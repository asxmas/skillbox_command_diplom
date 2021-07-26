package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.entity.projection.TagProjection;
import ru.skillbox.team13.mapper.TagMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.TagRepository;
import ru.skillbox.team13.service.TagService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getTags(String tag, int offset, int itemPerPage){
        log.debug("Searching tags by '{}'", tag);
        int page = offset / itemPerPage;
        Pageable pageable = PageRequest.of(page, itemPerPage);
        List<Tag> tags = tagRepository.findTagsByTag(pageable, tag);

        return WrapperMapper
                .wrap(tags.stream()
                        .map(TagMapper::mapToTagDto)
                        .collect(Collectors.toList())
                        , countByName(tag)
                        , offset
                        , itemPerPage
                        , true);
    }

    @Override
    @Transactional
    public TagDto addTag(TagDto tagDto){
        if(tagRepository.existsByTag(tagDto.getTag())){
            return TagMapper.mapToTagDto(tagRepository.findTagByTag(tagDto.getTag()));
        }
        Tag tag = tagDtoToTagMapper(tagDto);
        log.debug("Saving tag '{}'", tagDto.getTag());
        tagRepository.save(tag);
        return TagMapper.mapToTagDto(tag);
    }

    @Override
    @Transactional
    public Optional<String> deleteTag(int id){
        if(tagRepository.findById(id).isPresent()) {
            log.debug("Deleting tag id={}", id);
            tagRepository.deleteById(id);
        }
        return Optional.of("ok");
    }

    @Override
    public Set<Tag> getTagsByName(List<String> tagNames) {
        Set<Tag> tags = tagRepository.findAllByTagIn(tagNames);

        if (tags.isEmpty()) {
            tags = tagNames.stream().map(Tag::new).collect(Collectors.toSet());
        } else {
            Map<String, Tag> nameTagMap = tags.stream().collect(Collectors.toMap(Tag::getTag, t -> t));
            log.debug("Found existing tags: {}", nameTagMap.keySet());
            List<Tag> newTags = tagNames.stream().filter(tagName -> !nameTagMap.containsKey(tagName))
                    .map(Tag::new).collect(toList());
            tags.addAll(newTags);
        }
        return tags;
    }

    @Override
    public Map<Integer, Set<String>> getPostIdTagsMap(List<Integer> postIds) {
        Set<TagProjection> tags = tagRepository.findAllByPostsIn(postIds);

        log.debug("Found {} tag entries for {} posts.", tags.size(), postIds.size());
        return tags.stream().collect(Collectors.groupingBy
                (TagProjection::getPostId, Collectors.mapping(TagProjection::getName, Collectors.toSet())));
    }

    private int countByName(String tag) {
        return tagRepository.countTags(tag);
    }

    private static Tag tagDtoToTagMapper(TagDto tagDto){
        Tag tag = new Tag();
        tag.setTag(tagDto.getTag());
        return tag;
    }

}

