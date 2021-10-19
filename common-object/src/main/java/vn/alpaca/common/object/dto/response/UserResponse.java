package vn.alpaca.common.object.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {

    private int id;

    private String username;

    private Integer roleId;

    private String role;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    private List<String> phoneNumbers;

    private String email;

    private Date dateOfBirth;

    private String address;

}
