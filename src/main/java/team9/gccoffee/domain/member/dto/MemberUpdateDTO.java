package team9.gccoffee.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {
    private Long id;
    private String name;
    private String email;
    private String postcode;
    private String address;
}
