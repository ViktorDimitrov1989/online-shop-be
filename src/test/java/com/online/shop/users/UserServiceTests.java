package com.online.shop.users;

import com.online.shop.areas.users.repositories.UserRepository;
import com.online.shop.areas.users.services.RoleService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class UserServiceTests {

    private UserRepository userRepository;

    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;


}
