package team9.gccoffee.domain.member.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.global.exception.MemberException;
import team9.gccoffee.global.exception.MemberTaskException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    Member member1;
    Member member2;
    Member saveMember1;
    Member saveMember2;

    @BeforeEach
        //더미데이터 만들고 시작
    void dummyInsert() {
        //더미데이터
        member1 = Member.builder()
                .memberId(1L)
                .name("고객더미1")
                .email("test@email.com")
                .address("테스트address")
                .postcode("테스트postcode")
                .memberType(MemberType.CUSTOMER)
                .build();
        member2 = Member.builder()
                .memberId(2L)
                .name("관리자더미")
                .email("admin@email.com")
                .address("관리자address")
                .postcode("관리자postcode")
                .memberType(MemberType.ADMIN)
                .build();

        //더미데이터 DB에 저장하는 코드
        saveMember1 = memberRepository.save(member1);
        saveMember2 = memberRepository.save(member2);
    }

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void getMemberById() {

        // Id로 찾은 멤버의 객체
        MemberResponseDTO findMember1 = memberService.getMemberById(saveMember1.getMemberId());
        MemberResponseDTO findMember2 = memberService.getMemberById(saveMember2.getMemberId());
        //테스트 시작
        assertThat(saveMember1.getMemberType()).isEqualTo(MemberType.CUSTOMER);
        assertThat(findMember1.getAddress()).isEqualTo(saveMember1.getAddress());
        assertThat(findMember1.getPostcode()).isEqualTo(saveMember1.getPostcode());
        assertThat(findMember1.getName()).isEqualTo(saveMember1.getName());
        assertThat(findMember1.getEmail()).isEqualTo(saveMember1.getEmail());

        assertThat(saveMember2.getMemberType()).isEqualTo(MemberType.ADMIN);
        assertThat(findMember2.getAddress()).isEqualTo(saveMember2.getAddress());
        assertThat(findMember2.getPostcode()).isEqualTo(saveMember2.getPostcode());
        assertThat(findMember2.getName()).isEqualTo(saveMember2.getName());
        assertThat(findMember2.getEmail()).isEqualTo(saveMember2.getEmail());

    }

    @Test
    void getAllMembers() {
        MemberPageRequestDTO requestDTO = MemberPageRequestDTO.builder()
                .page(1)
                .size(20)
                .build();
        Page<Member> members = memberService.getAllMembers(requestDTO);
        assertThat(members).isNotNull();
        assertThat(members.getTotalElements()).isEqualTo(2);
    }

    @Test
    void updateMember() {
        //DTO를 사용하여 수정
        MemberUpdateDTO updateDTO = new MemberUpdateDTO();
        updateDTO.setId(saveMember1.getMemberId());
        updateDTO.setName("업데이트된 이름");
        updateDTO.setEmail("updated@email.com");
        updateDTO.setPostcode("업데이트된 postcode");
        updateDTO.setAddress("업데이트된 address");

        //정보 반영
        memberService.updateMember(updateDTO);

        // DB에서 직접 조회하여 검증
        Member foundMember = memberRepository.findById(saveMember1.getMemberId()).orElse(null);
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getName()).isEqualTo("업데이트된 이름");
        assertThat(foundMember.getEmail()).isEqualTo("updated@email.com");
        assertThat(foundMember.getPostcode()).isEqualTo("업데이트된 postcode");
        assertThat(foundMember.getAddress()).isEqualTo("업데이트된 address");
    }

    @Test
    void createMember() {
        MemberRequestDTO requestDTO = new MemberRequestDTO();
        MemberRequestDTO requestDTO2 = new MemberRequestDTO();
        requestDTO.setAddress("신규생성");
        requestDTO.setName("신규멤버");
        requestDTO.setEmail("신규@email.com");
        requestDTO.setPostcode("우편번호");
        requestDTO.setMemberType(MemberType.CUSTOMER);

        MemberResponseDTO member1 = memberService.createMember(requestDTO);

        requestDTO2.setAddress("어드민주소");
        requestDTO2.setName("어드민네임");
        requestDTO2.setEmail("어드민@email.com");
        requestDTO2.setPostcode("어드민 우편번호");
        requestDTO2.setMemberType(MemberType.CUSTOMER);
        MemberResponseDTO member2 = memberService.createMember(requestDTO2);


        // DB에서 직접 확인하기
        Member memberDB1 = memberRepository.findByEmail(member1.getEmail()).orElse(null);

        // Assertions
        assertThat(memberDB1).isNotNull();
        assertThat(memberDB1.getName()).isEqualTo(requestDTO.getName());
        assertThat(memberDB1.getEmail()).isEqualTo(requestDTO.getEmail());

        // 어드민 멤버도 검증
        Member memberDB2 = memberRepository.findByEmail(member2.getEmail()).orElse(null);
        assertThat(memberDB2).isNotNull();
        assertThat(memberDB2.getName()).isEqualTo(requestDTO2.getName());
        assertThat(memberDB2.getEmail()).isEqualTo(requestDTO2.getEmail());
    }


    @Test
    void deleteMember() {

        memberService.deleteMember(member1.getMemberId());
        memberService.deleteMember(member2.getMemberId());

        assertThat(memberRepository.findAll()).isEmpty();
        //assertThatThrownBy(() -> memberRepository.findAll()).isInstanceOf(MemberTaskException.class);

        // 삭제된 회원을 조회할 때 예외 발생 확인
        assertThatThrownBy(() -> memberService.getMemberById(member1.getMemberId()))
                .isInstanceOf(MemberTaskException.class)
                .hasMessage("NOT_FOUND");

        assertThatThrownBy(() -> memberService.getMemberById(member2.getMemberId()))
                .isInstanceOf(MemberTaskException.class)
                .hasMessage("NOT_FOUND");
    }
}
