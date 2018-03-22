package com.online.shop.service;
import com.online.shop.dto.user.UserResponseDto;
import com.online.shop.model.binding.user.LoginUserBindingModel;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponseDto register(RegisterUserBindingModel model);

    UserResponseDto login(LoginUserBindingModel model);

    List<UserResponseDto> findAllUsers();
}
