package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.common.object.exception.ResourceNotFoundException;
import vn.alpaca.userservice.entity.Authority;
import vn.alpaca.userservice.repository.AuthorityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public Authority findById(int id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "There's no authority match with id " + id
                ));
    }
}
