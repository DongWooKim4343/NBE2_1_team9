package team9.gccoffee.domain.member.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team9.gccoffee.domain.member.domain.Member;

import java.util.Optional;
import team9.gccoffee.domain.member.dto.MemberResponseDTO;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Query("SELECT m FROM Member m")
    List<MemberResponseDTO> getMemberResponses(Pageable pageable);
}
