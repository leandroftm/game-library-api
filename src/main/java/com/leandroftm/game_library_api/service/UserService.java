package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.request.CreateUserRequest;
import com.leandroftm.game_library_api.domain.dto.request.UpdateUserRequest;
import com.leandroftm.game_library_api.domain.dto.response.UserResponse;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.exception.domain.user.PasswordConflictException;
import com.leandroftm.game_library_api.exception.domain.user.UserEmailAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNameAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNotFoundException;
import com.leandroftm.game_library_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUserNameIgnoreCase(request.userName())) {
            throw new UserNameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UserEmailAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(request.userName(), encodedPassword, request.email());
        userRepository.save(user);
    }

    //Test purposes
//    public Page<UserResponse> getUsers(Pageable pageable) {
//        return userRepository.findAll(pageable).map(UserResponse::new);
//    }

    public void updateUser(long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (request.userName() != null ) {
            if (userRepository.existsByUserNameIgnoreCaseAndIdNot(request.userName(), id)) {
                throw new UserNameAlreadyExistsException();
            }
        }

        if (request.email() != null) {
            if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
                throw new UserEmailAlreadyExistsException();
            }
        }

        if (request.password() != null && passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new PasswordConflictException();
        }

        String encodedPassword = request.password() != null ? passwordEncoder.encode(request.password()) : null;

        user.updateDetails(request.userName(), encodedPassword, request.email());
        userRepository.save(user);
    }

    public void enable(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.enable();
        userRepository.save(user);
    }

    public void disable(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.disable();
        userRepository.save(user);
    }

}
