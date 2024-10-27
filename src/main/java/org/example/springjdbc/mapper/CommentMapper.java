package org.example.springjdbc.mapper;

import org.example.springjdbc.dto.CommentRequest;
import org.example.springjdbc.entity.Comment;
import org.example.springjdbc.model.CommentResponse;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentResponse map(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .post_id(comment.getPost_id())
                .account(AccountMapper.toAccountResponse(comment.getAccount()))
                .content(comment.getContent())
                .created_at(comment.getCreated_at())
                .id_parent(comment.getId_parent())
                .name_user(comment.getName_user())
                .image(comment.getImage())
                .build();
    }

    public static List<CommentResponse> toListCommentResponse(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(map(comment));
        }
        return commentResponses;
    }

    public static Comment toComment(CommentRequest commentRequest) {
        return Comment.builder()
                .account(commentRequest.getAccount())
                .content(commentRequest.getContent())
                .created_at(commentRequest.getCreated_at())
                .id_parent(commentRequest.getId_parent())
                .name_user(commentRequest.getName_user())
                .post_id(commentRequest.getPost_id())
                .image(commentRequest.getImage())
                .build();
    }
}
