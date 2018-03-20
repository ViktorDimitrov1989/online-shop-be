package com.online.shop.service;

import com.online.shop.entity.Role;

public interface RoleService {

    Role findRoleByAuthority(String authority);

}
