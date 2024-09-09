package team9.gccoffee.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;
import team9.gccoffee.domain.member.repository.MemberRepository;
import team9.gccoffee.domain.order.domain.Order;
import team9.gccoffee.global.exception.MemberException;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    //등록
    @Test
    public void testInsert() {
        //given
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ; //range 는 마지막 값 포함 안되고 rangeClosed 는 마지막 값 포함된다.
            Member member = Member.builder()
                    .name("user" + i)
                    .email("user" + i + "@aaa.com")
                    .postcode("postcode" + i)
                    .address("address" + i)
                    .memberType(i <= 80 ? MemberType.CUSTOMER : MemberType.ADMIN)
                    .build();
            //회원 정보 등록 에서는 order insert 처리 안함.

            //when
            Member savedMember = memberRepository.save(member);

            //then
            assertNotNull(savedMember);
            if(i<=80) assertEquals(savedMember.getMemberType(), MemberType.CUSTOMER);
            else        assertEquals(savedMember.getMemberType(), MemberType.ADMIN);

        });
    }

    //조회
    //memberId 로 멤버 개별 조회
    @Test
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

    //멤버 전체 조회 - 페이징
    @Test
    public void testList() {
        //given
        Pageable pageable = PageRequest.of(0,
                20, Sort.by("memberId").ascending()); //한페이지에 20명씩
        //when
        Page<Member> memberListPage = memberRepository.findAll(pageable);
        assertNotNull( memberListPage );
        //then
        assertEquals(100, memberListPage.getTotalElements());
        assertEquals(5, memberListPage.getTotalPages());
        assertEquals(0, memberListPage.getNumber());
        assertEquals(20, memberListPage.getSize());
        assertEquals(20, memberListPage.getContent().size());

        memberListPage.getContent().forEach(System.out::println);

    }

    //개인 주문 조회 //order table 에서 memberid 에 해당하는 값 가져오는 것인데 여기서 가능한가?
//    @Test
//    public void testGetOrder() {
//        Long memberId = 3L;
//
//        Optional<List<Order>> foundOrders
//    }



    //수정
    @Test   //UPDATE 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testUpdate() {
        //given
        Long memberId = 2L;

        //when
        //memberId 가 2L 인 사용자를 데이터베이스에서 조회하여
        Optional<Member> foundMember = memberRepository.findById(memberId); //값 받아오기

        //조회 결과가 없으면 MemberTaskException 으로 NOT_FOUND 예외를 발생
        //값이 없다면 예외 던지기
        foundMember.orElseThrow(MemberException.NOT_FOUND::get);

        //assertTrue(foundMember.isPresent());

        //값이 있으면 변경
        foundMember.get().changeName("userChanged");
        foundMember.get().changeEmail("changed@bbb.com");
        foundMember.get().changePostcode("changedPostcode");
        foundMember.get().changeAddress("changedAddress");

        //then
        //CHANGE 되어있는지 확인
        assertEquals("changed@bbb.com", foundMember.get().getEmail());

    }


    //삭제
    @Test
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
