package team9.gccoffee.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team9.gccoffee.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}