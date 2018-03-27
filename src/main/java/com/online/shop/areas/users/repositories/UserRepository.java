package com.online.shop.areas.users.repositories;

import com.online.shop.areas.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmail(String email);

    User findOneByPhoneNumber(String phoneNumber);

    User findOneById(Long id);

    Page<User> findAllByIdNot(Pageable pageable, Long id);
}
