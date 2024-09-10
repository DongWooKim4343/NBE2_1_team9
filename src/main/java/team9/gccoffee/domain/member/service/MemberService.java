package team9.gccoffee.domain.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;


import java.util.Optional;

@Service

public interface MemberService {
    Optional<Member> getMemberById(Long memberId); //멤버 개별 조회
    Page<Member> getAllMembers(Pageable pageable); //전체 멤버 조회
    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO); //멤버 생성, 관리자 고객
    public Member updateMember(MemberUpdateDTO member); //멤버 수정
    public void deleteMember(Long memberId); //멤버 삭제



}
