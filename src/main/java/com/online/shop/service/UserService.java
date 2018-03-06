package com.online.shop.service;

import com.online.shop.dto.user.RegisterUserResponseDto;
import com.online.shop.model.binding.user.RegisterUserBindingModel;

public interface UserService {

    RegisterUserResponseDto register(RegisterUserBindingModel model);

}
