package org.example.springjdbc.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.model.PageModel;
import org.example.springjdbc.model.PostResponse;
import org.example.springjdbc.service.impl.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/home")
public class HomeController {
    PostService postService;

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(defaultValue = "1") int pageNumber,
                       @RequestParam(defaultValue = "4") int pageSize
    ) {
        PageModel pageModel = postService.pagination(pageNumber, pageSize);
        model.addAttribute("pageModel", pageModel);

        return "index";
    }

    @GetMapping("/{id}")
    public String detailPost(@PathVariable Long idPost, Model model) {
        PostResponse post = postService.findById(idPost);
        model.addAttribute("post", post);
        return "detailPost";
    }
}
