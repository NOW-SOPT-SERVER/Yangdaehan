package org.sopt.spring.service;

import lombok.RequiredArgsConstructor;
import org.sopt.spring.domain.Blog;
import org.sopt.spring.domain.Post;
import org.sopt.spring.repository.PostRepository;
import org.sopt.spring.service.dto.PostCreateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogService blogService;
    public String create(Long blogId, PostCreateRequest postCreateRequest) {
        Blog blog = blogService.findById(blogId);
        Post post = new Post(postCreateRequest.title(), postCreateRequest.content(),blog);
        post = postRepository.save(post);
        return post.getId().toString();
    }
}

