package org.sopt.spring.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.spring.common.dto.SuccessStatusResponse;
import org.sopt.spring.service.PostService;
import org.sopt.spring.service.dto.PostCreateRequest;
import org.sopt.spring.common.dto.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/posts")
    public ResponseEntity<SuccessStatusResponse> createPost(
            @RequestHeader Long blogId,
            @RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).header(
                        "Location",
                        postService.create(blogId, postCreateRequest))
                .body(SuccessStatusResponse.of(SuccessMessage.POST_CREATE_SUCCESS));
    }
}

