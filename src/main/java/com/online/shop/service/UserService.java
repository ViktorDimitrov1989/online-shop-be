package com.online.shop.service;
import com.online.shop.dto.user.RegisterUserResponseDto;
import com.online.shop.model.binding.user.LoginUserBindingModel;
import com.online.shop.model.binding.user.RegisterUserBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    RegisterUserResponseDto register(RegisterUserBindingModel model);

    RegisterUserResponseDto login(LoginUserBindingModel model);

}
