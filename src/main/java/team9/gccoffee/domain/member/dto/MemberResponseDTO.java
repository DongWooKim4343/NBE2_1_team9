package team9.gccoffee.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;

@Data
@NoArgsConstructor
public class MemberResponseDTO {

    private String name;
    private String email;
    private MemberType memberType;
    private String postcode;

    //response 쪽이니 dto 로 바꾸는 것만?
    public MemberResponseDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();

    }


}

