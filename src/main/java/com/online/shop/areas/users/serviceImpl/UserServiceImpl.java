package com.online.shop.areas.users.serviceImpl;

import com.online.shop.areas.users.dto.user.UserResponseDto;
import com.online.shop.areas.users.entities.Address;
import com.online.shop.areas.users.entities.Role;
import com.online.shop.areas.users.entities.User;
import com.online.shop.areas.users.enums.RoleType;
import com.online.shop.exception.RequestException;
import com.online.shop.areas.users.models.binding.user.LoginUserBindingModel;
import com.online.shop.areas.users.models.binding.user.RegisterUserBindingModel;
import com.online.shop.areas.users.repositories.UserRepository;
import com.online.shop.areas.users.services.AddressService;
import com.online.shop.areas.users.services.RoleService;
import com.online.shop.areas.users.services.UserService;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto register(RegisterUserBindingModel registrationModel) {

        if(this.userRepository.findOneByEmail(registrationModel.getEmail()) != null){
            throw new RequestException(ResponseMessageConstants.EMAIL_ALREADY_TAKEN, HttpStatus.BAD_REQUEST);
        }

        if(this.userRepository.findOneByPhoneNumber(registrationModel.getPhoneNumber()) != null){
            throw new RequestException(ResponseMessageConstants.PHONE_NUMBER_ALREADY_TAKEN, HttpStatus.BAD_REQUEST);
        }


        User user = this.modelMapper.map(registrationModel, User.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setRegisterDate(new Date());

        Role role = this.roleService.findRoleByAuthority(RoleType.ROLE_USER.name());

        Address addressToSave = new Address(registrationModel.getAdress(), registrationModel.getPostCode(), registrationModel.getCity(), registrationModel.getStreet());

        if(role == null){
            role = new Role();
            role.setAuthority(RoleType.ROLE_USER.name());
        }

        user.getAuthorities().add(role);
        user.setAddress(addressToSave);

        User resUser = this.userRepository.save(user);

        UserResponseDto respUser = this.modelMapper.map(resUser, UserResponseDto.class);

        respUser.setAuthorities(user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

        return respUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(ResponseMessageConstants.INVALID_CREDENTIALS);
        }

        return user;
    }

    @Override
    public UserResponseDto login(LoginUserBindingModel loginModel) {
        UserDetails user = this.loadUserByUsername(loginModel.getEmail());

        if (user == null || !this.bCryptPasswordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
            throw new RequestException(ResponseMessageConstants.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }

        if(!user.isAccountNonLocked()){
            throw new RequestException(ResponseMessageConstants.ACCOUNT_IS_LOCKED, HttpStatus.FORBIDDEN);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponseDto respUser = this.modelMapper.map(user, UserResponseDto.class);

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        respUser.setAuthorities(roles);

        return respUser;
    }

    @Override
    public Page<UserResponseDto> findAllUsers(Long loggedUserId, int page, int size) {
        Pageable pageCount = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Page<User> users = this.userRepository.findAll(pageCount);

        List<UserResponseDto> resp = new ArrayList<>();

        for (User user : users) {

            if(user.getId() == loggedUserId){
                continue;
            }

            UserResponseDto respUser = this.modelMapper.map(user, UserResponseDto.class);

            respUser.setAuthorities(user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

            resp.add(respUser);
        }

        Page<UserResponseDto> respPage = new PageImpl<>(resp, pageCount, this.userRepository.count());

        return respPage;
    }

    @Override
    public UserResponseDto makeUserAdminById(Long userId) {
        User user = this.findUserById(userId);

        boolean isAdmin = user.getAuthorities().stream().anyMatch(x -> x.getAuthority().equalsIgnoreCase(RoleType.ROLE_ADMIN.name()));

        if(isAdmin){
            throw new RequestException(ResponseMessageConstants.ALREADY_ADMIN, HttpStatus.BAD_REQUEST);
        }

        Role role = this.roleService.findRoleByAuthority(RoleType.ROLE_ADMIN.name());

        user.getAuthorities().add(role);

        User editedUser = this.userRepository.save(user);

        UserResponseDto resp = this.modelMapper.map(editedUser, UserResponseDto.class);
        resp.setAuthorities(editedUser.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

        return resp;
    }

    @Override
    public UserResponseDto blockUnblockUserById(Long userId, boolean unblock) {
        User user = this.findUserById(userId);

        user.setAccountNonLocked(unblock);

        User editedUser = this.userRepository.save(user);

        UserResponseDto resp = this.modelMapper.map(editedUser, UserResponseDto.class);
        resp.setAuthorities(editedUser.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

        return resp;
    }

    @Override
    public User findUserById(Long id) {
        User user = this.userRepository.findOneById(id);

        if(user == null){
            throw new RequestException(ResponseMessageConstants.INVALID_USER, HttpStatus.BAD_REQUEST);
        }

        return user;
    }

    @Override
    public UserResponseDto takeAdminPrivilegesById(Long userId) {
        User user = this.findUserById(userId);

        boolean isAdmin = user.getAuthorities().stream().anyMatch(x -> x.getAuthority().equalsIgnoreCase(RoleType.ROLE_ADMIN.name()));

        if(!isAdmin){
            throw new RequestException(ResponseMessageConstants.NOT_ADMIN, HttpStatus.BAD_REQUEST);
        }

        user.setAuthorities(user.getAuthorities().stream().filter(a -> !a.getAuthority().equalsIgnoreCase(RoleType.ROLE_ADMIN.name())).collect(Collectors.toSet()));

        User editedUser = this.userRepository.save(user);

        UserResponseDto resp = this.modelMapper.map(editedUser, UserResponseDto.class);
        resp.setAuthorities(editedUser.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

        return resp;
    }


}
