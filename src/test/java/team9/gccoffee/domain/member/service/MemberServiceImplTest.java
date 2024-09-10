package team9.gccoffee.domain.member.service;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void getMemberById() {
        //더미데이터
        Member member1 = Member.builder()
                .memberId(1L)
                .name("고객더미1")
                .email("test@email.com")
                .address("테스트address")
                .postcode("테스트postcode")
                .memberType(MemberType.CUSTOMER)
                .build();
        Member member2 = Member.builder()
                .memberId(2L)
                .name("관리자더미")
                .email("admin@email.com")
                .address("관리자address")
                .postcode("관리자postcode")
                .memberType(MemberType.ADMIN)
                .build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);

        Member findMember1 = memberService.getMemberById(saveMember1.getMemberId()).orElse(null);
        Member findMember2 = memberService.getMemberById(saveMember2.getMemberId()).orElse(null);

        assertThat(findMember1).isEqualTo(saveMember1);
        assertThat(findMember1.getMemberType()).isEqualTo(MemberType.CUSTOMER);
        assertThat(findMember1.getMemberId()).isEqualTo(saveMember1.getMemberId());

        assertThat(findMember2).isEqualTo(saveMember2);
        assertThat(findMember2.getMemberType()).isEqualTo(MemberType.ADMIN);
        assertThat(findMember2.getMemberId()).isEqualTo(saveMember2.getMemberId());

    }

    @Test
    void getAllMembers() {
    }

    @Test
    void updateMember() {
    }

    @Test
    void createMember() {
    }

    @Test
    void deleteMember() {
    }
}