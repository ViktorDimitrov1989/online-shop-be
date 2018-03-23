package com.online.shop.areas.users.serviceImpl;

import com.online.shop.areas.users.entities.Role;
import com.online.shop.areas.users.enums.RoleType;
import com.online.shop.areas.users.repositories.RoleRepository;
import com.online.shop.areas.users.services.RoleService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Role role = this.roleRepository.findByAuthority(authority);

        if(role == null){
            throw new RequestException(ResponseMessageConstants.INVALID_ROLE, HttpStatus.BAD_REQUEST);
        }

        return role;
    }
}
