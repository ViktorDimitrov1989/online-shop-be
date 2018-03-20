package com.online.shop.serviceImpl;

import com.online.shop.entity.Role;
import com.online.shop.enums.RoleType;
import com.online.shop.repository.RoleRepository;
import com.online.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByAuthority(String authority) {
        return this.roleRepository.findByAuthority(RoleType.valueOf(authority));
    }
}
