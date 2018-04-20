package com.online.shop.roles;


import com.online.shop.areas.users.entities.Role;
import com.online.shop.areas.users.repositories.RoleRepository;
import com.online.shop.areas.users.serviceImpl.RoleServiceImpl;
import com.online.shop.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RoleServiceTests {

    private static final String VALID_AUTHORITY = "USER";

    private static final String INVALID_AUTHORITY = "ALABALA";
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;

    @Before
    public void init(){
        this.testRole = new Role();
        this.testRole.setAuthority(VALID_AUTHORITY);
        this.testRole.setId(1L);

        doReturn(this.testRole).when(this.roleRepository).findByAuthority(VALID_AUTHORITY);
    }


    @Test
    public void testFindRoleByAuthority_WithValidAuthority_ShouldNotBeNull(){
        assertNotNull("Role is null.", this.roleService.findRoleByAuthority(VALID_AUTHORITY));
    }

    @Test(expected = RequestException.class)
    public void testFindRoleByAuthority_WithInvalidAuthority_ShouldThrowException(){
        this.roleService.findRoleByAuthority(INVALID_AUTHORITY);
    }





}
