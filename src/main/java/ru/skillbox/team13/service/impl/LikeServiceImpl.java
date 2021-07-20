package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LikesDto;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.projection.Liker;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.LikeRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.service.LikeService;
import ru.skillbox.team13.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserService userService;
    private final LikeRepository likeRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final PersonRepository personRepo;

    @Override
    public DTOWrapper isLikedBy(Integer personId, int itemId, String type) {
        Person liker = personId == null ?
                userService.getAuthorizedUser().getPerson() :
                personRepo.findById(personId).orElseThrow(() -> new BadRequestException("No person found for id=" + personId));

        LikesDto dto = new LikesDto(checkLikedBy(liker, itemId));
        return WrapperMapper.wrap(dto, true);
    }

    @Override
    public DTOWrapper getLikedBy(int itemId, String type) {
        List<Liker> likers = likeRepo.findLikersProjectionsForItemId(itemId);

        LikesDto dto = new LikesDto(likers.size());
        dto.setUsers(likers.stream().mapToInt(Liker::getLikerId).toArray());

        return WrapperMapper.wrap(dto, true);
    }

    @Override
    @Transactional
    public DTOWrapper doLike(int id, String type) {
        Person liker = userService.getAuthorizedUser().getPerson();

        if (checkLikedBy(liker, id)) {
            throw new BadRequestException("Person id=" + liker.getId() + " has already liked id=" + id);
        }

        switch (type) {
            case "Post" -> likePost(liker, postRepo.findById(id).get());
            case "Comment" -> likeComment(liker, commentRepo.findById(id).get());
            default -> throw new BadRequestException("Bad 'type' parameter");
        }

//        applyLike(liker, postOrComment);
//        log.debug("id={} liked {} (id={})", liker.getId(), type, id);
        return getLikedBy(id, type);
    }

    @Override
    @Transactional
    public DTOWrapper doDislike(int itemId, String type) {
        Person liker = userService.getAuthorizedUser().getPerson();
        if (!checkLikedBy(liker, itemId)) {
            throw new BadRequestException("Person id=" + liker.getId() + " has hasn't liked id=" + itemId);
        }

        log.debug("id={} removed like from {} (id={})", liker.getId(), type, itemId);
        likeRepo.deleteByLikerAndId(liker, itemId);

        return getLikedBy(itemId, type);
    }

    private boolean checkLikedBy(Person liker, int itemId) {
        return likeRepo.countByLikerAndItemId(liker, itemId) > 0;
    }

    private void likePost(Person liker, Post post) {
        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(liker);
        like.setPost(post);
        likeRepo.save(like);
    }
    private void likeComment(Person liker, Comment comment) {
        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(liker);
        like.setComment(comment);
        likeRepo.save(like);
    }
}
