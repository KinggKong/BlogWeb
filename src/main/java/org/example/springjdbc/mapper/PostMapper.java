package org.example.springjdbc.mapper;

import org.example.springjdbc.entity.Post;
import org.example.springjdbc.model.AccountResponse;
import org.example.springjdbc.model.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    public static PostResponse map(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .created_at(post.getCreated_at())
                .name_user(post.getName_user())
                .status(post.getStatus())
                .author(AccountMapper.toAccountResponse(post.getAuthor()))
                .build();
    }

    public static List<PostResponse> toPostResonseList(List<Post> posts) {
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(map(post));
        }
        return postResponses;
    }

}
