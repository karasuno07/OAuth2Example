package vn.alpaca.common.security.object;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SecurityRoleDetail {

    private int id;

    private String name;

    private Set<SecurityAuthorityDetail> authorities;

}
