package org.example.springjdbc.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.repository.LikePostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikePostService {
    LikePostRepository likePostRepository;

    public int likePost(Long idPost, Long idAccount) {
        return likePostRepository.likePost(idPost, idAccount);
    }

    public int unlikePost(Long idPost, Long idAccount) {
        return likePostRepository.dislikePost(idPost, idAccount);
    }

    public boolean checkLiked(Long idPost, Long idAccount) {
        return likePostRepository.checkLikedPost(idPost, idAccount);
    }

    public Long countLikeByPostId(Long idPost) {
        return likePostRepository.countLikeByPostID(idPost);
    }
}
