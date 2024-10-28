package org.example.springjdbc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.dto.AccountResponse;
import org.example.springjdbc.dto.PageModel;
import org.example.springjdbc.entity.Account;
import org.example.springjdbc.model.PostRequest;
import org.example.springjdbc.dto.PostResponse;
import org.example.springjdbc.service.impl.CategoryService;
import org.example.springjdbc.service.impl.LikePostService;
import org.example.springjdbc.service.impl.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/posts")
public class PostController {
    PostService postService;
    CategoryService categoryService;
    LikePostService likePostService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model, HttpSession session) {
        PostResponse post = postService.findById(id);
        boolean checkLiked = false;
        if (session.getAttribute("user") != null) {
            AccountResponse account = (AccountResponse) session.getAttribute("user");
            checkLiked = likePostService.checkLiked(id, account.getId());
        }
        model.addAttribute("liked", checkLiked);
        model.addAttribute("post", post);
        return "detailPost";
    }

    @GetMapping("/favorite/{id}")
    public String category(@PathVariable Long id, Model model,
                           @RequestParam(defaultValue = "1") int pageNumber,
                           @RequestParam(defaultValue = "4") int pageSize) {
        PageModel pageModel = postService.findByFavouriteAccount(id, pageNumber, pageSize);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("categoryId", id);
        return "favorite";
    }

    @PostMapping("")
    public String save(@ModelAttribute("postRequest") PostRequest postRequest, Model model) {
        return "index";
    }

    @ModelAttribute("countReact")
    public Long countReact() {
        return categoryService.countPostByCategoryId(1L);
    }

    @ModelAttribute("countJS")
    public Long countJS() {
        return categoryService.countPostByCategoryId(3L);
    }

    @ModelAttribute("countCSS")
    public Long countCSS() {
        return categoryService.countPostByCategoryId(2L);
    }

    @ModelAttribute("countWeb")
    public Long countWeb() {
        return categoryService.countPostByCategoryId(4L);
    }

    @ModelAttribute("top5Recent")
    public List<PostResponse> top5Recent() {
        return postService.top5Recent();
    }

}
