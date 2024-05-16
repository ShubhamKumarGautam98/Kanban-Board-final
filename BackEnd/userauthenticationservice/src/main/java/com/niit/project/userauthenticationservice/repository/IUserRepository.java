package com.niit.project.userauthenticationservice.repository;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserDetails,String> {
    Optional<UserDetails> findByEmailIdAndPassword(String emailId, String password);

}
