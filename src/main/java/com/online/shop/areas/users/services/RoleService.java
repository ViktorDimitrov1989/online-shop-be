package com.online.shop.areas.users.services;

import com.online.shop.areas.users.entities.Role;

public interface RoleService {

    Role findRoleByAuthority(String authority);

}
