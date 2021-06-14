package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface LikeService {
    DTOWrapper isLikedBy(Integer personId, int itemId, String type);

    DTOWrapper getLikedBy(int itemId, String type);
}
