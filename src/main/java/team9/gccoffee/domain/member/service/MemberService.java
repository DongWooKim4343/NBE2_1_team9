package team9.gccoffee.domain.member.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;

import java.util.Optional;
import team9.gccoffee.domain.order.domain.Order;

@Service

public interface MemberService {
    public MemberResponseDTO getMemberById(Long memberId); //멤버 개별 조회
    public Page<Member> getAllMembers(MemberPageRequestDTO memberPageRequestDTO); //전체 멤버 조회
    public List<Order> getOrdersForMember(Long memberId);

    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO); //멤버 생성, 관리자 고객
    public MemberResponseDTO updateMember(MemberUpdateDTO memberUpdateDTO); //멤버 수정
    public void deleteMember(Long memberId); //멤버 삭제



}
