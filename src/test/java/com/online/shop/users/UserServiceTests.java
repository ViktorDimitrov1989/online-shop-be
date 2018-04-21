package com.online.shop.users;

import com.online.shop.areas.users.dto.user.UserResponseDto;
import com.online.shop.areas.users.entities.Role;
import com.online.shop.areas.users.entities.User;
import com.online.shop.areas.users.enums.RoleType;
import com.online.shop.areas.users.models.binding.user.LoginUserBindingModel;
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
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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

    private static final String USER_AUTHORITY = "ROLE_USER";
    private static final String USER_INVALID_EMAIL = "invalidEmail@mail.bg";
    private static final String NEW_USER_EMAIL = "viktor@mail.bg";
    private static final Long LOGGED_USER_ID = 1L;
    private static final Long INVALID_USER_ID = 100L;


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;


    private RegisterUserBindingModel testRegisterUserBindingModel;

    private LoginUserBindingModel testLoginUser;


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

        this.testLoginUser = new LoginUserBindingModel();
        this.testLoginUser.setEmail(USER_EMAIL);
        this.testLoginUser.setPassword(USER_PASSWORD);

        when(this.userRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

    }

    @Test(expected = RequestException.class)
    public  void testRegisterUser_WithDuplicateEmail_ShouldThrowException(){
        doReturn(new User()).when(this.userRepository).findOneByEmail(this.testRegisterUserBindingModel.getEmail());

        this.userService.register(this.testRegisterUserBindingModel);
    }

    @Test(expected = RequestException.class)
    public  void testRegisterUser_WithDuplicatePhoneNumber_ShouldThrowException(){
        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(new User());

        this.userService.register(this.testRegisterUserBindingModel);
    }

    @Test
    public  void testRegisterUser_WithValidData_UserShouldContainAtLeastUserRole(){
        this.testRegisterUserBindingModel.setEmail(NEW_USER_EMAIL);

        UserResponseDto respUser = this.userService.register(this.testRegisterUserBindingModel);

        assertTrue("USER_ROLE doesn't exist in the response object.", respUser.getAuthorities().contains(USER_AUTHORITY));
    }

    @Test
    public  void testRegisterUser_WithValidData_AddressShouldNotBeNull(){
        this.testRegisterUserBindingModel.setEmail(NEW_USER_EMAIL);

        UserResponseDto respUser = this.userService.register(this.testRegisterUserBindingModel);

        assertNotNull("Address is null.", respUser.getAddress());
    }

    @Test
    public  void testLoadUserByUsername_WithValidData_ShouldNotBeNull(){
        User testUserResp = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);

        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(testUserResp);

        assertNotNull("User is null when correct email provided.", this.userService.loadUserByUsername(USER_EMAIL));
    }

    @Test(expected = UsernameNotFoundException.class)
    public  void testLoadUserByUsername_WithInvalidData_ShouldThrowException(){
        assertNotNull("Exception was not thrown when user is fetched with wrong email.",
                this.userService.loadUserByUsername(USER_INVALID_EMAIL));
    }

    @Test
    public  void testLoginUser_WithValidData_ShouldNotReturnNull(){
        String encryptedPass = this.bCryptPasswordEncoder.encode(this.testRegisterUserBindingModel.getPassword());

        this.testRegisterUserBindingModel.setPassword(encryptedPass);

        User testUserResp = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        testUserResp.setAccountNonLocked(true);

        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(testUserResp);

        assertNotNull("Returned value from login func is null.",
                this.userService.login(this.testLoginUser));
    }

    @Test(expected = UsernameNotFoundException.class)
    public  void testLoginUser_WithInvalidEmail_ShouldThrowException(){
        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(null);

        this.userService.login(this.testLoginUser);
    }

    @Test(expected = RequestException.class)
    public  void testLoginUser_WithInvalidPassword_ShouldThrwException(){
        User testUserResp = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);

        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(testUserResp);

        this.userService.login(this.testLoginUser);
    }

    @Test(expected = RequestException.class)
    public  void testLoginUser_WithLockedAccount_ShouldThrowException(){
        String encryptedPass = this.bCryptPasswordEncoder.encode(this.testRegisterUserBindingModel.getPassword());

        this.testRegisterUserBindingModel.setPassword(encryptedPass);

        User testUserResp = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        testUserResp.setAccountNonLocked(false);

        when(this.userRepository.findOneByEmail(USER_EMAIL))
                .thenReturn(testUserResp);

        this.userService.login(this.testLoginUser);
    }

    @Test
    public void testFindAllUsers_WhenThereAreTwoUsers_ShouldNotBeEmpty(){
        Pageable pageCount = PageRequest.of(0, 1, Sort.Direction.ASC, "id");

        User loggedUser = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        Role loggedUserRole = new Role();
        loggedUserRole.setAuthority(USER_AUTHORITY);

        loggedUser.setAuthorities(new HashSet<>(){{add(loggedUserRole);}});

        when(this.userRepository.findAllByIdNot(pageCount, LOGGED_USER_ID))
                .thenReturn(new PageImpl<User>(new ArrayList<>(){{add(loggedUser);}}));

        when(this.userRepository.count())
                .thenReturn(2L);

        Page<UserResponseDto> respPage = this.userService.findAllUsers(LOGGED_USER_ID,0,1);

        assertTrue("Users collection is empty.", !respPage.getContent().isEmpty());
    }

    @Test
    public void testFindAllUsers_WhenThereIsOneUser_ShouldBeEmpty(){
        Pageable pageCount = PageRequest.of(0, 1, Sort.Direction.ASC, "id");

        User loggedUser = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        Role loggedUserRole = new Role();
        loggedUserRole.setAuthority(USER_AUTHORITY);

        loggedUser.setAuthorities(new HashSet<>());

        when(this.userRepository.findAllByIdNot(pageCount, LOGGED_USER_ID))
                .thenReturn(new PageImpl<User>(new ArrayList<>()));

        when(this.userRepository.count())
                .thenReturn(1L);

        Page<UserResponseDto> respPage = this.userService.findAllUsers(LOGGED_USER_ID,0,1);

        assertTrue("Users collection is not empty.", respPage.getContent().isEmpty());
    }

    @Test(expected = RequestException.class)
    public void testMakeUserAdminById_WithInvalidId_ShouldThrowException(){
        when(this.userRepository.findOneById(INVALID_USER_ID))
                .thenReturn(null);

        this.userService.makeUserAdminById(INVALID_USER_ID);
    }

    @Test(expected = RequestException.class)
    public void testMakeUserAdminById_WithUserThatIsAlreadyAdmin_ShouldThrowException(){
        User userToMakeAdmin = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        Role role = new Role();
        role.setAuthority(RoleType.ROLE_ADMIN.name());

        userToMakeAdmin.setAuthorities(new HashSet<>(){{add(role);}});

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToMakeAdmin);

        this.userService.makeUserAdminById(LOGGED_USER_ID);
    }

    @Test
    public void testMakeUserAdminById_WithValidUser_ShouldHaveAdminRoleAdded(){
        User userToMakeAdmin = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        userToMakeAdmin.setAuthorities(new HashSet<>());

        Role role = new Role();
        role.setAuthority(RoleType.ROLE_ADMIN.name());

        doReturn(role).when(this.roleService).findRoleByAuthority(RoleType.ROLE_ADMIN.name());

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToMakeAdmin);

        when(this.userRepository.save(userToMakeAdmin)).thenReturn(userToMakeAdmin);

        UserResponseDto respUser = this.userService.makeUserAdminById(LOGGED_USER_ID);

        assertTrue("User don't have ROLE_ADMIN.", respUser.getAuthorities().contains(RoleType.ROLE_ADMIN.name()));
    }

    @Test
    public void testBlockUserById_WithValidUserId_ShouldIsAccountNonLockedFalse(){
        User userToLock = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToLock);

        when(this.userRepository.save(userToLock)).thenReturn(userToLock);

        UserResponseDto respUser = this.userService.blockUnblockUserById(LOGGED_USER_ID, false);

        assertTrue("User's account is not locked.", !respUser.isAccountNonLocked());
    }

    @Test
    public void testUnblockUserById_WithValidUserId_ShouldIsAccountNonLockedTrue(){
        User userToLock = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        userToLock.setAccountNonLocked(false);

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToLock);

        when(this.userRepository.save(userToLock)).thenReturn(userToLock);

        UserResponseDto respUser = this.userService.blockUnblockUserById(LOGGED_USER_ID, true);

        assertTrue("User's account is still locked.", respUser.isAccountNonLocked());
    }


    @Test
    public void testTakeUserAdminPrivilegesById_WithValidUser_ShouldNotHaveAdminRoleAdded(){
        Role role = new Role();
        role.setAuthority(RoleType.ROLE_ADMIN.name());

        User userToTakeAdminPrivileges = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        userToTakeAdminPrivileges.setAuthorities(new HashSet<>() {{add(role);}});

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToTakeAdminPrivileges);

        when(this.userRepository.save(userToTakeAdminPrivileges)).thenReturn(userToTakeAdminPrivileges);

        UserResponseDto respUser = this.userService.takeAdminPrivilegesById(LOGGED_USER_ID);

        assertTrue("User still have ROLE_ADMIN.", !respUser.getAuthorities().contains(RoleType.ROLE_ADMIN.name()));
    }

    @Test(expected = RequestException.class)
    public void testTakeUserAdminPrivilegesById_WithUserThatIsNotAdmin_ShouldThrowException(){
        User userToTakeAdminPrivileges = this.modelMapper.map(this.testRegisterUserBindingModel, User.class);
        userToTakeAdminPrivileges.setAuthorities(new HashSet<>());

        when(this.userRepository.findOneById(LOGGED_USER_ID))
                .thenReturn(userToTakeAdminPrivileges);

        this.userService.takeAdminPrivilegesById(LOGGED_USER_ID);
    }

}
