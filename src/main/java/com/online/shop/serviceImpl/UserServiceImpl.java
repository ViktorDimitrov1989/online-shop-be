package com.online.shop.serviceImpl;

import com.online.shop.dto.user.RegisterUserResponseDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public RegisterUserResponseDto register(RegisterUserBindingModel registrationModel) {

        if(this.userRepository.findOneByEmail(registrationModel.getEmail()) != null){
            throw new RequestException(ResponseMessageConstants.EMAIL_ALREADY_TAKEN);
        }

        if(this.userRepository.findOneByPhoneNumber(registrationModel.getPhoneNumber()) != null){
            throw new RequestException(ResponseMessageConstants.PHONE_NUMBER_ALREADY_TAKEN);
        }


        User user = this.modelMapper.map(registrationModel, User.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setRegisterDate(new Date());

        Role role = this.roleService.findRoleByAuthority(RoleType.USER.name());

        Address addressToSave = new Address(registrationModel.getAdress(), registrationModel.getPostCode(), registrationModel.getCity(), registrationModel.getStreet());

        if(role == null){
            role = new Role();
            role.setAuthority(RoleType.USER);
        }

        user.getAuthorities().add(role);
        user.setAddress(addressToSave);

        User resUser = this.userRepository.save(user);

        RegisterUserResponseDto respUser = this.modelMapper.map(resUser, RegisterUserResponseDto.class);

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
    public RegisterUserResponseDto login(LoginUserBindingModel loginModel) {
        User user = this.userRepository.findOneByEmail(loginModel.getEmail());

        if (user == null || !this.bCryptPasswordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
            throw new RequestException(ResponseMessageConstants.INVALID_CREDENTIALS);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        RegisterUserResponseDto respUser = this.modelMapper.map(user, RegisterUserResponseDto.class);

        List<String> roles = user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList());
        respUser.setRoles(roles);

        return respUser;
    }

}
