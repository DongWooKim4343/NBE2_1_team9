package team9.gccoffee.domain.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new MemberResponseDTO(member);
    }

    @Override
    public List<MemberResponseDTO> getAllMembers(MemberPageRequestDTO memberPageRequestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 해당 member 의 memberType 체크 하여 관리자인 경우 전체 조회 가능
        if (member.getMemberType() == MemberType.ADMIN) {
            Sort sort = Sort.by("memberId").descending();
            Pageable pageable = memberPageRequestDTO.getPageable(sort);

            return memberRepository.getMemberResponses(pageable);
        } else {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_VALID);
        }
    }

    @Override
    public MemberResponseDTO updateMember(MemberUpdateDTO memberUpdateDTO) {
        Member member = memberRepository.findById(memberUpdateDTO.getId())
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        try {
            member.changeName(memberUpdateDTO.getName());
            member.changeEmail(memberUpdateDTO.getEmail());
            member.changePostcode(memberUpdateDTO.getPostcode());
            member.changeAddress(memberUpdateDTO.getAddress());

            return new MemberResponseDTO(member);
        } catch (Exception e) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_MODIFIED);
        }
    }

    @Override
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        try {
            memberRepository.delete(member);
        } catch (Exception e) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_REMOVED);
        }
    }

    @Override
    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) {
        if (memberRequestDTO.getMemberType() == MemberType.ADMIN && !"ADMIN000".equals(memberRequestDTO.getAdminCode())) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_VALID);
        }
        try {
            Member member = memberRequestDTO.toEntity();
            memberRepository.save(member);

            return new MemberResponseDTO(member);
        } catch (Exception e) {
            throw new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_REGISTERED);
        }
    }
}
