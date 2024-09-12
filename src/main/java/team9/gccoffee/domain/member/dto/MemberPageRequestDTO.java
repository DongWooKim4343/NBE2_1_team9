package team9.gccoffee.domain.member.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPageRequestDTO {

    @Builder.Default        //페이지 기본 값 설정
    @Min(1)
    private int page = 1;

    @Builder.Default
    @Min(20)
    @Max(100)
    private int size = 20;  // 한 페이지 회원 수 20 이하면 20으로 지정

    //페이지 번호, 페이지 회원목록 수 , 정렬 순서를 Pageable 객체로 반환
    public Pageable getPageable(Sort sort) {
        int pageNum = page < 0 ? 1 : page - 1;
        int sizeNum = size <= 20 ? 20 : size;

        return PageRequest.of(pageNum, sizeNum, sort);
    }
}
