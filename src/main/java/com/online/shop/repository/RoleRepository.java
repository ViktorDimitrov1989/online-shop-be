package com.online.shop.repository;

import com.online.shop.entity.Role;
import com.online.shop.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(RoleType authority);

}
