package com.online.shop.serviceImpl;

import com.online.shop.entity.Role;
import com.online.shop.entity.User;
import com.online.shop.enums.RoleType;
import com.online.shop.model.user.RegisterUserBindingModel;
import com.online.shop.repository.UserRepository;
import com.online.shop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void register(RegisterUserBindingModel registrationModel) {
        User user = this.modelMapper.map(registrationModel, User.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        Role role = new Role();
        role.setAuthority(RoleType.USER.name());

        user.getAuthorities().add(role);

        this.userRepository.save(user);
    }
}
