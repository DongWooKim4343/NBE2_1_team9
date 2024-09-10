package team9.gccoffee.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;

import static team9.gccoffee.domain.member.domain.MemberType.CUSTOMER;


@Getter
@Setter
public class MemberRequestDTO {

    @NotEmpty(message = "이름은 필수 입럭값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입럭값입니다.")
    private String email;

    @NotEmpty(message = "우편번호는 필수 입럭값입니다.")
    private String postcode;

    @NotNull(message = "관리자일경우만 관리자 코드입력")
    private String address;

    @NotNull(message = "관리자일경우만 관리자 코드입력")
    private MemberType memberType = MemberType.CUSTOMER;  // 기본 값 설정

    private String adminCode;

    //request 쪽이니 toEntity
    public Member toEntity() {
        return Member.builder().name(name)
                .email(email)
                .postcode(postcode)
                .address(address)
                .memberType(memberType)
                .build();
    }



}
