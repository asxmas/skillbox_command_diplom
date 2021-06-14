package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LikesDto;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.RepoLike;
import ru.skillbox.team13.repository.projection.Liker;
import ru.skillbox.team13.service.LikeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserServiceImpl userService; //todo fix implementation
    private final RepoLike likeRepo;

    @Override
    public DTOWrapper isLikedBy(Integer personId, int itemId, String type) {
        if (personId == null) {
            personId = userService.getAuthorizedUser().getPerson().getId();
        }

        Optional<Like> o = likeRepo.getLikeForPostOrCommentById(itemId, personId);

        return WrapperMapper.wrapSingleData(new LikesDto(o.isPresent()));
    }

    @Override
    public DTOWrapper getLikedBy(int itemId, String type) {
        List<Liker> likers = likeRepo.findLikersProjectionsForItemId(itemId);
        LikesDto dto = new LikesDto(likers.size());
        dto.setUsers(likers.stream().mapToInt(Liker::getLikerId).toArray());
        return WrapperMapper.wrapSingleData(dto);
    }
}
