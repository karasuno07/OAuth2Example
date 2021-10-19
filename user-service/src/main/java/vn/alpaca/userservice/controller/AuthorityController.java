package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.jpa.util.ExtractParam;
import vn.alpaca.common.object.dto.response.AuthorityResponse;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.userservice.entity.Authority;
import vn.alpaca.userservice.mapper.AuthorityMapper;
import vn.alpaca.userservice.service.AuthorityService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService service;
    private final AuthorityMapper mapper;

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping
    public SuccessResponse<List<AuthorityResponse>> getAuthorities() {

        List<AuthorityResponse> dto = service.findAll()
                .stream()
                .map(mapper::convertToResponseDTO)
                .collect(Collectors.toList());

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<AuthorityResponse> getAuthority(
            @PathVariable("id") int id) {
        Authority authority = service.findById(id);
        AuthorityResponse dto = mapper.convertToResponseDTO(authority);
        return new SuccessResponse<>(dto);
    }
}