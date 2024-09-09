package team9.gccoffee.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;

import java.util.Optional;
import team9.gccoffee.global.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Member updateMember(MemberUpdateDTO memberUpdateDTO) {

        return null;
    }

    @Override
    public Member createMember(MemberRequestDTO memberRequestDTO) {
        if (memberRequestDTO.getMemberType() == MemberType.ADMIN) {
            if (!"ADMIN000".equals(memberRequestDTO.getAdminCode())) {
                throw new SecurityException("Invalid admin code!!");
            }
        }



        return null;
    }


    @Override
    public void deleteMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        Member member = foundMember.orElseThrow(MemberException.NOT_FOUND::get);

        try {
            memberRepository.deleteById(memberId);
        } catch (Exception e) {
            throw MemberException.NOT_REMOVED.get();
        }
    }
}
