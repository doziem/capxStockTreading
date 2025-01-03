package com.doziem.capxStockTreading.service;

import com.doziem.capxStockTreading.dto.StockDto;
import com.doziem.capxStockTreading.dto.UserDto;
import com.doziem.capxStockTreading.exception.AlreadyExistException;
import com.doziem.capxStockTreading.exception.ResourceNotFoundException;
import com.doziem.capxStockTreading.model.User;
import com.doziem.capxStockTreading.repository.UserRepository;
import com.doziem.capxStockTreading.request.UserUpdateRequest;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    public  UserRepository userRepository;

    @Override
    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new AlreadyExistException("User already exists with email: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest user) {

        return userRepository.findById(userId)
                .map(existingUser->updateExistingUser(existingUser, user))
                .map(userRepository::save)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    private User updateExistingUser(User existingUser, UserUpdateRequest request){
        existingUser.setFullName(request.getFullName());
        existingUser.setEmail(request.getEmail());

        return existingUser;
    }

    @Override
    @Transactional
    public List<UserDto> getAllUser() {
        List<User> user =  userRepository.findAll();
        return user.stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
      userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, ()->{throw new ResourceNotFoundException("User not found");});
    }
}
