package team9.gccoffee.domain.member.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;

@Service
public interface MemberService {
    MemberResponseDTO getMemberById(Long memberId); //멤버 개별 조회
    List<MemberResponseDTO> getAllMembers(MemberPageRequestDTO memberPageRequestDTO, Long memberId); // 관리자 전체 멤버 조회

    MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO); //멤버 생성, 관리자 고객
    MemberResponseDTO updateMember(MemberUpdateDTO memberUpdateDTO); //멤버 수정
    void deleteMember(Long memberId); //멤버 삭제
}
