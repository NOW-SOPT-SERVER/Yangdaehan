package org.sopt.practice.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.auth.redis.domain.Token;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.domain.Member;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.repository.RedisTokenRepository;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.service.dto.MemberFindDto;
import org.sopt.practice.service.dto.UserJoinResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;


    @Transactional
    public UserJoinResponse createMember(
        MemberCreateDto memberCreate
    ) {
        Member member = memberRepository.save(
            Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age())
        );
        Long memberId = member.getId();

        String accessToken = jwtTokenProvider.issueAccessToken(
            UserAuthentication.createUserAuthentication(memberId)
        );
        String refreshToken = jwtTokenProvider.issueRefreshToken(
            UserAuthentication.createUserAuthentication(memberId)
        );

        // Redis에 refresh token 저장
        Token token = Token.builder()
            .id(memberId)
            .refreshToken(refreshToken)
            .build();
        redisTokenRepository.save(token);

        return UserJoinResponse.of(accessToken,refreshToken, memberId.toString());
    }

    public MemberFindDto findMemberById(Long memberId) {
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
            () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        ));
    }

    @Transactional
    public void deleteMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
            //() -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
            () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
    }
}