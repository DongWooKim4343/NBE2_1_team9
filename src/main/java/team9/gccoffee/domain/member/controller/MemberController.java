package team9.gccoffee.domain.member.controller;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
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
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.service.MemberService;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.global.exception.MemberException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Log4j2
public class MemberController {

    private final MemberService memberService;

    //등록
    @Operation(
            summary = "멤버 등록"
            , description = "멤버를 등록하는 API. 관리자로 등록을 원할시 관리자 코드 입력이 필요함. memberType에 'CUSTOMER', 'ADMIN'만 입력가능 ")

    @PostMapping
    public ResponseEntity<MemberResponseDTO> register(
            @Validated @RequestBody MemberRequestDTO memberRequestDTO) {

        return ResponseEntity.ok(memberService.createMember(memberRequestDTO));
    }


    //조회 ////////////
    //memberId 로 멤버 개별 조회
    @Operation(
            summary = "멤버 조회"
            , description = "멤버를 조회하는 API. memberId 로 멤버 개별 조회.")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> read(
            @PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }


    //멤버 전체 조회 - memberId 로 관리자인가 확인
    @Operation(
            summary = "멤버 전체 조회"
            , description = "멤버 전체를 조회하는 API. MemberType 이 관리자여야만 조회 가능함.")
    @GetMapping("/admin/{memberId}")
    public ResponseEntity<Page<Member>> getMemberList(
            @Validated MemberPageRequestDTO memberPageRequestDTO,
            @PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(memberService.getAllMembers(memberPageRequestDTO, memberId));

    }



    //개인 주문 조회 myOrders/{memberId}
    @Operation(
            summary = "개인 주문 조회"
            , description = "멤버 주문 내역을 조회하는 API, memberId로 주문조회")
    @GetMapping("/myOrders/{memberId}")
    public ResponseEntity<List<Order>> getMemberOrderList(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.getOrdersForMember(memberId));
    }


    ////////////

    //수정 memberId
    @Operation(
            summary = "멤버 수정"
            , description = "멤버의 주문 내역을 조회하는 API, memberId로 수정")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> modify(
            @Validated @RequestBody MemberUpdateDTO memberUpdateDTO,
            @PathVariable("memberId") Long memberId ) {

        if( !memberId.equals(memberUpdateDTO.getId())) {
            throw MemberException.NOT_MATCHED.get();
        }

        return ResponseEntity.ok(memberService.updateMember(memberUpdateDTO));
    }


    //삭제 memberId
    @Operation(
            summary = "멤버 삭제"
            , description = "멤버의 주문 내역을 조회하는 API, memberId로 삭제")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Map<String, String>> remove(
            @PathVariable("memberId") Long mid) {

        memberService.deleteMember(mid);

        return ResponseEntity.ok(Map.of("result", "success"));
    }

}
