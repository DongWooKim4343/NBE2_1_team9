package team9.gccoffee.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.dto.MemberPageRequestDTO;
import team9.gccoffee.domain.member.dto.MemberRequestDTO;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;
import team9.gccoffee.domain.member.dto.MemberUpdateDTO;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceImplTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    Member member1;
    Member member2;
    Member saveMember1;
    Member saveMember2;

    @BeforeEach
    void dummyInsert() {
        member1 = createMember(1L, "고객더미1", "test@email.com", "테스트 address", "테스트 postcode", MemberType.CUSTOMER);
        member2 = createMember(2L, "관리자더미", "admin@email.com", "관리자 address", "관리자 postcode", MemberType.ADMIN);

        saveMember1 = memberRepository.save(member1);
        saveMember2 = memberRepository.save(member2);
    }

    @Order(1)
    @Test
    void getMemberById() {
        // Given
        Long memberId1 = saveMember1.getMemberId();

        // When
        MemberResponseDTO findMember1 = memberService.getMemberById(memberId1);

        // Then
        assertThat(findMember1.getMemberType()).isEqualTo(MemberType.CUSTOMER);
        assertThat(findMember1.getAddress()).isEqualTo(saveMember1.getAddress());
        assertThat(findMember1.getPostcode()).isEqualTo(saveMember1.getPostcode());
        assertThat(findMember1.getName()).isEqualTo(saveMember1.getName());
        assertThat(findMember1.getEmail()).isEqualTo(saveMember1.getEmail());
    }

    @Order(2)
    @Test
    void getAllMembers() {
        // Given
        MemberPageRequestDTO requestDTO = new MemberPageRequestDTO();
        Long adminMemberId = saveMember2.getMemberId(); // 관리자 멤버

        List<Member> expectedMembers = Arrays.asList(saveMember1, saveMember2);

        // When
        List<MemberResponseDTO> members = memberService.getAllMembers(requestDTO, adminMemberId);

        // Then
        assertThat(members).isNotEmpty();
        assertThat(members.size()).isEqualTo(expectedMembers.size());
    }

    @Order(3)
    @Test
    void updateMember() {
        // given
        String name = "업데이트된 이름";
        String email = "updated@email.com";
        String postcode = "업데이트된 postcode";
        String address = "업데이트된 address";
        MemberUpdateDTO updateDTO = new MemberUpdateDTO(saveMember1.getMemberId(), name, email, postcode, address);

        // when
        memberService.updateMember(updateDTO);
        Member foundMember = memberRepository.findById(saveMember1.getMemberId()).get();

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getName()).isEqualTo("업데이트된 이름");
        assertThat(foundMember.getEmail()).isEqualTo("updated@email.com");
        assertThat(foundMember.getPostcode()).isEqualTo("업데이트된 postcode");
        assertThat(foundMember.getAddress()).isEqualTo("업데이트된 address");
    }

    @Order(4)
    @Test
    void createMember() {
        // given
        MemberRequestDTO requestDTO = new MemberRequestDTO();
        requestDTO.setAddress("신규생성");
        requestDTO.setName("신규멤버");
        requestDTO.setEmail("신규@email.com");
        requestDTO.setPostcode("우편번호");
        requestDTO.setMemberType(MemberType.CUSTOMER);

        MemberRequestDTO requestDTO2 = new MemberRequestDTO();
        requestDTO2.setAddress("어드민주소");
        requestDTO2.setName("어드민네임");
        requestDTO2.setEmail("어드민@email.com");
        requestDTO2.setPostcode("어드민 우편번호");
        requestDTO2.setMemberType(MemberType.CUSTOMER);

        // when
        MemberResponseDTO member1 = memberService.createMember(requestDTO);
        MemberResponseDTO member2 = memberService.createMember(requestDTO2);

        Member memberDB1 = memberRepository.findByEmail(member1.getEmail()).get();
        Member memberDB2 = memberRepository.findByEmail(member2.getEmail()).get();

        // then
        assertThat(memberDB1).isNotNull();
        assertThat(memberDB1.getName()).isEqualTo(requestDTO.getName());
        assertThat(memberDB1.getEmail()).isEqualTo(requestDTO.getEmail());

        assertThat(memberDB2).isNotNull();
        assertThat(memberDB2.getName()).isEqualTo(requestDTO2.getName());
        assertThat(memberDB2.getEmail()).isEqualTo(requestDTO2.getEmail());
    }

    @Order(5)
    @Test
    void deleteMember() {
        // given
        Long memberId1 = saveMember1.getMemberId();
        Long memberId2 = saveMember2.getMemberId();

        // when
        memberService.deleteMember(memberId1);
        memberService.deleteMember(memberId2);

        // then
        assertThatThrownBy(() -> memberService.getMemberById(memberId1))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessage("회원 정보를 찾을 수 없습니다.");

        assertThatThrownBy(() -> memberService.getMemberById(memberId2))
                .isInstanceOf(GcCoffeeCustomException.class)
                .hasMessage("회원 정보를 찾을 수 없습니다.");
    }

    private Member createMember(Long id, String name, String email, String address, String postcode, MemberType memberType) {
        return Member.builder()
                .memberId(id)
                .name(name)
                .email(email)
                .address(address)
                .postcode(postcode)
                .memberType(memberType)
                .build();
    }
}
