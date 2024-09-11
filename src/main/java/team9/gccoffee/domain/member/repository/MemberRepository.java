package team9.gccoffee.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team9.gccoffee.domain.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.orderList WHERE m.memberId = :memberId")
    Optional<Member> findByIdWithOrders(@Param("memberId") Long memberId);

}
