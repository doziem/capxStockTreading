package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.UserDto;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User updateUser(Long userId, UserUpdateRequest user);

    List<UserDto> getAllUser();

    User getUser(Long userId);

    void deleteUser(Long userId);

}
