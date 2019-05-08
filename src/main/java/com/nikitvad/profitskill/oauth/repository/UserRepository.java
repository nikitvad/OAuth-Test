package com.nikitvad.profitskill.oauth.repository;

import com.nikitvad.profitskill.oauth.model.AppUserDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUserDetails, Long> {
    Optional<AppUserDetails> findByUsername(String userName);

    List<AppUserDetails> findAll();
}
