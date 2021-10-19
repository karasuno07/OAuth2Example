package vn.alpaca.common.object.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleResponse {

    private int id;

    private String name;

    private Set<AuthorityResponse> authorities;

}
