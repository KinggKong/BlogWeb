package org.example.springjdbc.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.dto.CommentRequest;
import org.example.springjdbc.entity.Account;
import org.example.springjdbc.entity.Comment;
import org.example.springjdbc.mapper.CommentMapper;
import org.example.springjdbc.model.CommentResponse;
import org.example.springjdbc.repository.AccountRepository;
import org.example.springjdbc.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    CommentRepository commentRepository;
    AccountRepository accountRepository;

    public List<CommentResponse> findAll() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponse> responses = CommentMapper.toListCommentResponse(comments);
        for (CommentResponse commentResponse : responses) {
            commentResponse.setReplyComment(CommentMapper.toListCommentResponse(commentRepository.findChildrenComment(commentResponse.getId())));
        }
        return responses;
    }

    public List<CommentResponse> findByPostId(Long postID) {
        List<Comment> comments = commentRepository.findByPostId(postID);
        List<CommentResponse> responses = CommentMapper.toListCommentResponse(comments);
        for (CommentResponse response : responses) {
            response.setReplyComment(CommentMapper.toListCommentResponse(commentRepository.findChildrenComment(response.getId())));
        }
        return responses;
    }

    public int addComment(CommentRequest commentRequest) {
        Comment comment = CommentMapper.toComment(commentRequest);
        return commentRepository.insertComment(comment);
    }
}
