package vn.alpaca.common.object.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
public class RoleRequest {

    @NotBlank(message = "blank")
    private String name;

    private Set<Integer> authorityIds;
}
