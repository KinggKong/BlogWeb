package org.example.springjdbc.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.dto.PageModel;
import org.example.springjdbc.dto.PostResponse;
import org.example.springjdbc.model.PostRequest;
import org.example.springjdbc.service.impl.CategoryService;
import org.example.springjdbc.service.impl.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/home")
public class HomeController {
    PostService postService;
    CategoryService categoryService;

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(defaultValue = "1") int pageNumber,
                       @RequestParam(defaultValue = "4") int pageSize
    ) {
        PageModel pageModel = postService.pagination(pageNumber, pageSize);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("postRequest", new PostRequest());
        return "index";
    }

    @GetMapping("/{id}")
    public String detailPost(@PathVariable Long idPost, Model model) {
        PostResponse post = postService.findById(idPost);
        model.addAttribute("post", post);
        return "detailPost";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keySearch, Model model,
                         @RequestParam(defaultValue = "1") int pageNumber,
                         @RequestParam(defaultValue = "4") int pageSize) {
        PageModel pageModel = postService.paginationSearch(pageNumber, pageSize, keySearch);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("keySearch", keySearch);
        return "resultSearch";
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable Long id, Model model,
                           @RequestParam(defaultValue = "1") int pageNumber,
                           @RequestParam(defaultValue = "4") int pageSize) {
        PageModel pageModel = postService.findByCategory(id, pageNumber, pageSize);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("categoryId", id);
        return "category";
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
