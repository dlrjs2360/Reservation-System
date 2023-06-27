package com.numble.reservationsystem.repository;


import com.numble.reservationsystem.entity.domain.User;
import com.numble.reservationsystem.repository.Custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
