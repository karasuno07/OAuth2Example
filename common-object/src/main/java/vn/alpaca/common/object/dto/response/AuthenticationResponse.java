package vn.alpaca.common.object.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationResponse {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private boolean active;
}
