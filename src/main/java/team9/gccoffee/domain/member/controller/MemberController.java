package team9.gccoffee.domain.member.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.dto.PageRequestDTO;
import team9.gccoffee.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Log4j2
public class MemberController {

    private final MemberService memberService;

    //등록
    @PostMapping
    public ResponseEntity<MemberResponseDTO> register(
            @Validated @RequestBody MemberRequestDTO memberRequestDTO) {

        //어떤 예외 처리 필요?

        return ResponseEntity.ok(memberService.createMember(memberRequestDTO));
    }


    //조회 ////////////
    //memberId 로 멤버 개별 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> read(
            @PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }


    //멤버 전체 조회
    @GetMapping
    public ResponseEntity<Page<Member>> getMemberList(
            @Validated PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(memberService.getAllMembers(pageRequestDTO));

    }



    //개인 주문 조회 myOrders/{memberId}
    @GetMapping("/myOrders/{memberId}")
    public ResponseEntity<Member> getMemberOrderList(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.)
    }



    ////////////

    //수정 memberId
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> modify(
            @Validated @RequestBody MemberUpdateDTO memberUpdateDTO,
            @PathVariable("memberId") Long memberId ) {

        return ResponseEntity.ok(memberService.updateMember(memberUpdateDTO));
    }




    //삭제 memberId
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Map<String, String>> remove(
            @PathVariable("memberId") Long mid) {

        Long memberId = memberService.getMemberById(mid).get().getMemberId();


        memberService.deleteMember(mid);

        return ResponseEntity.ok(Map.of("result", "success"));
    }

}
