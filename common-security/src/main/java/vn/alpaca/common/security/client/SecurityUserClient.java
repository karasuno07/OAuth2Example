package vn.alpaca.common.security.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.common.security.object.SecurityAuthorityDetail;
import vn.alpaca.common.security.object.SecurityRoleDetail;
import vn.alpaca.common.security.object.SecurityUserDetail;

import java.util.List;

@Headers({
        "Accept: application/json; charset=utf-8",
        "Content-Type: application/json"})
@FeignClient(value = "user-service")
public interface SecurityUserClient {

    @GetMapping(path = "/users/search/username")
    SuccessResponse<SecurityUserDetail> findByUserName(
            @RequestParam("val") String username);

    @GetMapping(path = "/users/{id}")
    SuccessResponse<SecurityUserDetail> findById(@PathVariable("id") int id);

    @GetMapping(path = "/authorities")
    SuccessResponse<List<SecurityAuthorityDetail>> getAuthorities();

    @GetMapping(path = "/roles/{roleId}")
    SuccessResponse<SecurityRoleDetail> getRoleDetail(
            @PathVariable("roleId") int id);

}
