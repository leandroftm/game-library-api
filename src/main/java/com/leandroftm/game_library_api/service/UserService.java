package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.request.CreateUserRequest;
import com.leandroftm.game_library_api.domain.dto.response.UserResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.exception.domain.user.UserEmailAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNameAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNotFoundException;
import com.leandroftm.game_library_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUserName(request.userName())) {
            throw new UserNameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UserEmailAlreadyExistsException();
        }

        User user = new User(request.userName(), request.password(), request.email());
        userRepository.save(user);
    }

    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::new);
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::new)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse getUserByName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void updateUser(long id, String userName, String password, String email) {
        if(userName != null) {
            if(userRepository.existsByUserNameAndIdNot(userName, id)){
                throw new UserNameAlreadyExistsException();
            }
        }

        if(email != null) {
            if(userRepository.existsByEmailAndIdNot(email, id)){
                throw new UserEmailAlreadyExistsException();
            }
        }

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.updateDetails(userName, password, email);
        userRepository.save(user);
    }

    public void enable(long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.enable();
        userRepository.save(user);
    }

    private void disable(long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.disable();
        userRepository.save(user);
    }

}
