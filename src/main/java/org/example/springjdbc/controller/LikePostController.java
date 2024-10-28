package org.example.springjdbc.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.dto.LikeResponse;
import org.example.springjdbc.service.impl.LikePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/post")
public class LikePostController {
    LikePostService likePostService;

    @GetMapping("/toggleLike")
    public ResponseEntity<LikeResponse> likePost(@RequestParam Long idPost,
                                                 @RequestParam Long idAccount) {
        boolean isLiked = likePostService.checkLiked(idPost, idAccount);
        if (isLiked) {
            likePostService.unlikePost(idPost, idAccount);
            return ResponseEntity.ok(new LikeResponse(false));
        } else {
            likePostService.likePost(idPost, idAccount);
            return ResponseEntity.ok(new LikeResponse(true));
        }
    }

}
