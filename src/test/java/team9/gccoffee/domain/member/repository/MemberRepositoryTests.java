package team9.gccoffee.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.global.exception.ErrorCode;
import team9.gccoffee.global.exception.GcCoffeeCustomException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member 객체를 저장할 수 있어야 한다.")
    @Commit
    @Order(1)
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            //given
            Member member = Member.builder()
                    .name("user" + i)
                    .email("user" + i + "@aaa.com")
                    .postcode("postcode" + i)
                    .address("address" + i)
                    .memberType(i <= 80 ? MemberType.CUSTOMER : MemberType.ADMIN)
                    .build();

            //when
            Member savedMember = memberRepository.save(member);

            //then
            assertNotNull(savedMember);
            if (i <= 80) {
                assertEquals(savedMember.getMemberType(), MemberType.CUSTOMER);
            } else {
                assertEquals(savedMember.getMemberType(), MemberType.ADMIN);
            }
        });
    }

    @Test
    @DisplayName("Member 객체를 조회할 수 있어야 한다.")
    @Order(2)
    public void testRead() {
        //given
        Long memberId = 3L;

        //when
        Optional<Member> foundMember = memberRepository.findById(memberId);
        assertTrue(foundMember.isPresent());

        //then
        Member member = foundMember.get();
        assertEquals(memberId, member.getMemberId());
    }

    @Test
    @DisplayName("Member 다수 객체를 조회할 수 있어야 한다.")
    @Order(3)
    public void testList() {
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.by("memberId").ascending());

        //when
        Page<Member> memberListPage = memberRepository.findAll(pageable);
        assertNotNull(memberListPage);

        //then
        assertEquals(100, memberListPage.getTotalElements());
        assertEquals(5, memberListPage.getTotalPages());
        assertEquals(0, memberListPage.getNumber());
        assertEquals(20, memberListPage.getSize());
        assertEquals(20, memberListPage.getContent().size());
    }

    @Test
    @DisplayName("Member 객체를 수정할 수 있어야 한다.")
    @Order(4)
    public void testUpdate() {
        //given
        Long memberId = 2L;

        //when
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        foundMember.changeName("userChanged");
        foundMember.changeEmail("changed@bbb.com");
        foundMember.changePostcode("changedPostcode");
        foundMember.changeAddress("changedAddress");

        // 다시 조회 시
        foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GcCoffeeCustomException(ErrorCode.MEMBER_NOT_FOUND));

        //then
        assertEquals("changed@bbb.com", foundMember.getEmail());
        assertThat(foundMember.getEmail()).isEqualTo("changed@bbb.com");
    }

    @Test
    @DisplayName("Member 객체를 삭제할 수 있어야 한다.")
    @Order(5)
    public void testDeleteById() {
        //given
        Long memberId = 4L;

        //when
        assertTrue(memberRepository.findById(memberId).isPresent());
        memberRepository.deleteById(memberId);

        //then
        assertFalse(memberRepository.findById(memberId).isPresent());
    }
}
