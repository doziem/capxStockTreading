package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User updateUser(Long userId, UserUpdateRequest user);

    List<User> getAllUser();

    void deleteUser(Long userId);

}
