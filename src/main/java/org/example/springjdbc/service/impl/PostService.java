package org.example.springjdbc.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Post;
import org.example.springjdbc.mapper.PostMapper;
import org.example.springjdbc.dto.CommentResponse;
import org.example.springjdbc.dto.PageModel;
import org.example.springjdbc.dto.PostResponse;
import org.example.springjdbc.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    CommentService commentService;
    LikePostService likePostService;

    public List<PostResponse> findAll(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        pageNumber -= 1;
        List<Post> posts = postRepository.findAll(pageNumber, pageSize);
        List<PostResponse> postResponses = PostMapper.toPostResonseList(posts);
        for (PostResponse postResponse : postResponses) {
            postResponse.setTotalComments(postRepository.countCommentByPost(postResponse.getId()));
        }
        return postResponses;
    }

    public PageModel pagination(int pageNumber, int pageSize) {
        int currentPage = pageNumber;
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        pageNumber -= 1;
        List<Post> posts = postRepository.findAll(pageNumber, pageSize);
        List<PostResponse> postResponses = PostMapper.toPostResonseList(posts);
        for (PostResponse postResponse : postResponses) {
            postResponse.setTotalComments(postRepository.countCommentByPost(postResponse.getId()));
            postResponse.setTotalLikes(likePostService.countLikeByPostId(postResponse.getId()));
        }
        int totalPages = (int) Math.ceil((double) postRepository.countAllPost() / pageSize);
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return PageModel.<PostResponse>builder()
                .currentPage(currentPage)
                .totalPage(totalPages)
                .data(postResponses)
                .pageNumbers(pageNumbers)
                .build();
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id);
        PostResponse postResponse = PostMapper.map(post);
        List<CommentResponse> commentResponses = commentService.findByPostId(id);
        postResponse.setComments(commentResponses);
        postResponse.setTotalComments(postRepository.countCommentByPost(id));
        return postResponse;
    }

    public Long countAllCommentByPostId(Long postId) {
        return postRepository.countCommentByPost(postId);
    }

    public Long countAllPost() {
        return postRepository.countAllPost();
    }

    public PageModel paginationSearch(int pageNumber, int pageSize, String keySearch) {
        int currentPage = pageNumber;
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        pageNumber -= 1;
        List<Post> posts = postRepository.search(keySearch, pageNumber, pageSize);
        List<PostResponse> postResponses = PostMapper.toPostResonseList(posts);
        for (PostResponse postResponse : postResponses) {
            postResponse.setTotalComments(postRepository.countCommentByPost(postResponse.getId()));
            postResponse.setTotalLikes(likePostService.countLikeByPostId(postResponse.getId()));
        }
        int totalPages = (int) Math.ceil((double) postRepository.countSearch(keySearch) / pageSize);
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return PageModel.<PostResponse>builder()
                .currentPage(currentPage)
                .totalPage(totalPages)
                .data(postResponses)
                .pageNumbers(pageNumbers)
                .build();
    }

    public List<PostResponse> top5Recent() {
        List<Post> posts = postRepository.top5Recent();
        return PostMapper.toPostResonseList(posts);
    }

    public PageModel findByCategory(Long idCategory, int pageNumber, int pageSize) {
        int currentPage = pageNumber;
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        pageNumber -= 1;
        List<Post> posts = postRepository.findByCategory(idCategory, pageNumber, pageSize);
        List<PostResponse> postResponses = PostMapper.toPostResonseList(posts);
        for (PostResponse postResponse : postResponses) {
            postResponse.setTotalComments(postRepository.countCommentByPost(postResponse.getId()));
            postResponse.setTotalLikes(likePostService.countLikeByPostId(postResponse.getId()));
        }
        int totalPages = (int) Math.ceil((double) postRepository.countByIdCategory(idCategory) / pageSize);
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return PageModel.<PostResponse>builder()
                .currentPage(currentPage)
                .totalPage(totalPages)
                .data(postResponses)
                .pageNumbers(pageNumbers)
                .build();
    }

    public PageModel findByFavouriteAccount(Long idAccount, int pageNumber, int pageSize) {
        int currentPage = pageNumber;
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        pageNumber -= 1;
        List<Post> posts = postRepository.findByMyFavorite(idAccount, pageNumber, pageSize);
        List<PostResponse> postResponses = PostMapper.toPostResonseList(posts);
        for (PostResponse postResponse : postResponses) {
            postResponse.setTotalComments(postRepository.countCommentByPost(postResponse.getId()));
            postResponse.setTotalLikes(likePostService.countLikeByPostId(postResponse.getId()));
        }
        int totalPages = (int) Math.ceil((double) postRepository.countMyFavorite(idAccount) / pageSize);
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return PageModel.<PostResponse>builder()
                .currentPage(currentPage)
                .totalPage(totalPages)
                .data(postResponses)
                .pageNumbers(pageNumbers)
                .build();
    }



}
