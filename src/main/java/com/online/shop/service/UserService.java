package com.online.shop.service;

import com.online.shop.model.user.RegisterUserBindingModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void register(RegisterUserBindingModel model);

}
