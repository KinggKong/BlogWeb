package org.example.springjdbc.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.model.PostResponse;
import org.example.springjdbc.service.impl.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/posts")
public class PostController {
    PostService postService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        PostResponse post = postService.findById(id);
        model.addAttribute("post", post);
        return "detailPost";
    }

}
