package org.example.springjdbc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.model.CommentRequest;
import org.example.springjdbc.mapper.AccountMapper;
import org.example.springjdbc.dto.AccountResponse;
import org.example.springjdbc.dto.CommentResponse;
import org.example.springjdbc.service.impl.AccountService;
import org.example.springjdbc.service.impl.CommentService;
import org.example.springjdbc.service.impl.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/comments")
public class CommentController {
    CommentService commentService;
    AccountService accountService;
    PostService postService;
    private final String FOLDER = "static/images";

    @GetMapping("/{id}")
    public List<CommentResponse> findAllByPostId(@PathVariable Long id) {
        return commentService.findByPostId(id);
    }

    @PostMapping("")
    public void addComment(@RequestPart CommentRequest commentRequest,
                           @RequestParam(value = "image", required = false) MultipartFile image, HttpSession session
    ) throws IOException {
        AccountResponse accSession = (AccountResponse) session.getAttribute("user");
        if (accSession == null) {
            AccountResponse accountResponse = accountService.findById(1L);
            commentRequest.setAccount(AccountMapper.toAccount(accountResponse));
            commentRequest.setName_user("john_doe");
        } else {
            commentRequest.setAccount(AccountMapper.toAccount(accSession));
            commentRequest.setName_user(accSession.getUsername());
        }
        commentRequest.setImage(saveImage(image));
        int result = commentService.addComment(commentRequest);
    }


    @GetMapping("/total/{id}")
    public Long totalCommentByPost(@PathVariable("id") Long postId) {
        return postService.countAllCommentByPostId(postId);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fullImageName = "";
        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            String uploadDirPath = "E:\\CY_VietNam\\SpringJDBC\\src\\main\\resources\\static\\images";

            File uploadFile = new File(uploadDirPath, fileName);
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            image.transferTo(uploadFile);
            fullImageName = "/images/" + fileName;

        }
        return fullImageName;
    }

}
