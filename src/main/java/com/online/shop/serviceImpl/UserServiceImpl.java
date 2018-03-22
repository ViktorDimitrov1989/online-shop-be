package com.online.shop.serviceImpl;

import com.online.shop.dto.user.UserResponseDto;
import com.online.shop.entity.Address;
import com.online.shop.entity.Role;
import com.online.shop.entity.User;
import com.online.shop.enums.RoleType;
import com.online.shop.exception.RequestException;
import com.online.shop.model.binding.user.LoginUserBindingModel;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import com.online.shop.repository.UserRepository;
import com.online.shop.service.AddressService;
import com.online.shop.service.RoleService;
import com.online.shop.service.UserService;
import com.online.shop.util.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private AddressService addressService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           AddressService addressService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.addressService = addressService;
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

        respUser.setRoles(user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()));

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

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponseDto respUser = this.modelMapper.map(user, UserResponseDto.class);

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        respUser.setRoles(roles);

        return respUser;
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        List<User> users = this.userRepository.findAll();

        Type listType = new TypeToken<List<UserResponseDto>>() {}.getType();

        return this.modelMapper.map(users, listType);
    }

}
