package com.online.shop.serviceImpl;

import com.online.shop.model.user.RegisterUserBindingModel;
import com.online.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void register(RegisterUserBindingModel model) {

    }
}
