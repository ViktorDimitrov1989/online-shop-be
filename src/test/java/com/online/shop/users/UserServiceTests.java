package com.online.shop.users;

import com.online.shop.areas.users.entities.User;
import com.online.shop.areas.users.models.binding.user.RegisterUserBindingModel;
import com.online.shop.areas.users.repositories.UserRepository;
import com.online.shop.areas.users.serviceImpl.UserServiceImpl;
import com.online.shop.areas.users.services.RoleService;
import com.online.shop.areas.users.services.UserService;
import com.online.shop.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class UserServiceTests {

    private static final String USER_ADDRESS = "23";
    private static final String USER_CITY = "Sofia";
    private static final String USER_EMAIL = "vd@mail.bg";
    private static final String USER_FIRST_NAME = "Pesho";
    private static final String USER_LAST_NAME = "Petrov";
    private static final String USER_PASSWORD = "12345";
    private static final String USER_CONFIRM_PASSWORD = "12345";
    private static final String USER_PHONE_NUMBER = "0888888888";
    private static final Integer USER_POST_CODE = 1614;
    private static final String USER_STREET = "Don";


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;


    private RegisterUserBindingModel testRegisterUserBindingModel;


    @Before
    public void init(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.modelMapper = new ModelMapper();

        this.userService = new UserServiceImpl(
                this.userRepository,
                this.roleService,
                this.bCryptPasswordEncoder,
                this.modelMapper
                );

        this.testRegisterUserBindingModel = new RegisterUserBindingModel();
        this.testRegisterUserBindingModel.setAdress(USER_ADDRESS);
        this.testRegisterUserBindingModel.setCity(USER_CITY);
        this.testRegisterUserBindingModel.setEmail(USER_EMAIL);
        this.testRegisterUserBindingModel.setFirstName(USER_FIRST_NAME);
        this.testRegisterUserBindingModel.setLastName(USER_LAST_NAME);
        this.testRegisterUserBindingModel.setPassword(USER_PASSWORD);
        this.testRegisterUserBindingModel.setConfirmPassword(USER_CONFIRM_PASSWORD);
        this.testRegisterUserBindingModel.setPhoneNumber(USER_PHONE_NUMBER);
        this.testRegisterUserBindingModel.setPostCode(USER_POST_CODE);
        this.testRegisterUserBindingModel.setStreet(USER_STREET);


    }

    @Test(expected = RequestException.class)
    public  void testRegisterUser_WithDuplicateEmail_ShouldThrowException(){
        doReturn(new User()).when(this.userRepository).findOneByEmail(this.testRegisterUserBindingModel.getEmail());

        this.userService.register(this.testRegisterUserBindingModel);
    }

    @Test(expected = RequestException.class)
    public  void testRegisterUser_WithDuplicatePhoneNumber_ShouldThrowException(){
        doReturn(new User()).when(this.userRepository).findOneByPhoneNumber(this.testRegisterUserBindingModel.getPhoneNumber());

        this.userService.register(this.testRegisterUserBindingModel);
    }


}
