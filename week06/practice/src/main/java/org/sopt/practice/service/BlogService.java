package org.sopt.practice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Member;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.BlogRepository;
import org.sopt.practice.repository.RedisTokenRepository;
import org.sopt.practice.service.dto.BlogCreateRequest;
import org.sopt.practice.service.dto.BlogTitleUpdateRequest;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberService memberService;
    private final RedisTokenRepository redisTokenRepository;


    public String create(Long memberId, BlogCreateRequest blogCreateRequest, String accessToken, String refreshToken) {
        // 유효한 access token 확인 및 갱신
        String validAccessToken = TokenService.validateAndRefreshAccessToken(accessToken, refreshToken);

        // member 찾기
        Member member = memberService.findById(memberId);

        // blog 생성 및 저장
        Blog blog = blogRepository.save(Blog.create(member, blogCreateRequest.title(), blogCreateRequest.description()));

        // 블로그 ID 반환
        return blog.getId().toString();
    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest newTitle) {
        Blog target = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND));
        target.patch(newTitle);
        blogRepository.save(target);
    }
}
