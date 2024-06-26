package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.service.MemberService;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.service.dto.MemberFindDto;
import org.sopt.practice.service.dto.UserJoinResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

//    @PostMapping
//    public ResponseEntity createMember(@RequestBody MemberCreateDto memberCreate) {
//        return ResponseEntity.created(URI.create(memberService.createMember(memberCreate))).build();
//    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberFindDto> findMemberById(@PathVariable Long memberId){
        return  ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMemberById(@PathVariable Long memberId){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserJoinResponse> postMember(
        @RequestBody MemberCreateDto memberCreate
    ) {
        UserJoinResponse userJoinResponse = memberService.createMember(memberCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", userJoinResponse.userId())
            .body(userJoinResponse);
    }
}
