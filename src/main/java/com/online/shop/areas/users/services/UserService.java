package com.online.shop.areas.users.services;
import com.online.shop.areas.users.dto.user.UserResponseDto;
import com.online.shop.areas.users.entities.User;
import com.online.shop.areas.users.models.binding.user.LoginUserBindingModel;
import com.online.shop.areas.users.models.binding.user.RegisterUserBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponseDto register(RegisterUserBindingModel model);

    UserResponseDto login(LoginUserBindingModel model);

    Page<UserResponseDto> findAllUsers(Long loggedUserId, int page, int size);

    UserResponseDto makeUserAdminById(Long userId);

    UserResponseDto blockUnblockUserById(Long userId, boolean block);

    User findUserById(Long id);

    UserResponseDto takeAdminPrivilegesById(Long userId);

}
