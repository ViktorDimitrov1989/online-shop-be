package com.online.shop.areas.users.repositories;

import com.online.shop.areas.users.entities.Role;
import com.online.shop.areas.users.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);

}
