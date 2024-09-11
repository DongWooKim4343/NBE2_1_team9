package team9.gccoffee.domain.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team9.gccoffee.domain.member.domain.Member;

@Data
@NoArgsConstructor
public class MemberUpdateDTO { //request

    private Long id;
    private String name;
    private String email;
    private String postcode;
    private String address;


    public Member toEntity() {
        return Member.builder().name(name)
                .email(email)
                .postcode(postcode)
                .address(address)
                .build();
    }

}
