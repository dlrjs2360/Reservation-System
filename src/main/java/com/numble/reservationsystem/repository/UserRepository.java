package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.repository.Custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
