package team9.gccoffee.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import team9.gccoffee.domain.member.domain.Member;
import team9.gccoffee.domain.member.domain.MemberType;

@Data
@NoArgsConstructor
public class MemberResponseDTO { //response

    private Long memberId;
    private String name;
    private String email;
    private String postcode;
    private String address;
    private MemberType memberType;


    public MemberResponseDTO(Member member) {

        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.postcode = member.getPostcode();
        this.address = member.getAddress();
        this.memberType = member.getMemberType();

    }


}

