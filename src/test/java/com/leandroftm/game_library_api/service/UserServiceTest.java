package com.leandroftm.game_library_api.service;

import com.leandroftm.game_library_api.domain.dto.request.CreateUserRequest;
import com.leandroftm.game_library_api.domain.dto.request.UpdateUserRequest;
import com.leandroftm.game_library_api.domain.entity.User;
import com.leandroftm.game_library_api.exception.domain.user.PasswordConflictException;
import com.leandroftm.game_library_api.exception.domain.user.UserEmailAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNameAlreadyExistsException;
import com.leandroftm.game_library_api.exception.domain.user.UserNotFoundException;
import com.leandroftm.game_library_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private final long id = 1L;
    private final String encodedPassword = "encoded-password";

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest(
                "userName",
                "password",
                "email@email.com"
        );

        when(userRepository.existsByUserNameIgnoreCase(request.userName())).thenReturn(false);

        when(userRepository.existsByEmail(request.email())).thenReturn(false);

        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);

        userService.createUser(request);

        verify(userRepository).save(argThat(user ->
                user.getUserName().equals(request.userName()) &&
                        user.getPassword().equals(encodedPassword) &&
                        user.getEmail().equals(request.email())
        ));
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @SuppressWarnings("")
//    @Test
//    void shouldReturnAllUsersSuccessfully() {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<User> users = List.of(newUser());
//
//        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
//
//        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
//
//        Page<UserResponse> response = userService.getUsers(pageable);
//
//        assertNotNull(response);
//        assertEquals("userName", response.getContent().getFirst().userName());
//        assertEquals("email@email.com", response.getContent().getFirst().email());
//        verifyNoMoreInteractions(userRepository);
//    }

    @Test
    void shouldUpdateUserNameSuccessfully() {
        UpdateUserRequest request = new UpdateUserRequest(
                "newUserName",
                null,
                null
        );

        User savedUser = newUser();
        ReflectionTestUtils.setField(savedUser, "id", id);


        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        when(userRepository.existsByUserNameIgnoreCaseAndIdNot(request.userName(), id)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.updateUser(id, request);

        verify(userRepository).findById(id);
        verify(userRepository).existsByUserNameIgnoreCaseAndIdNot(request.userName(), id);
        verify(userRepository).save(argThat(user ->
                user.getUserName().equals(request.userName())));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldUpdateEmailSuccessfully() {
        UpdateUserRequest request = new UpdateUserRequest(
                null,
                null,
                "new.email@email.com"
        );

        User savedUser = newUser();
        ReflectionTestUtils.setField(savedUser, "id", id);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        when(userRepository.existsByEmailAndIdNot(request.email(), id)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.updateUser(id, request);

        verify(userRepository).findById(id);
        verify(userRepository).existsByEmailAndIdNot(request.email(), id);
        verify(userRepository).save(argThat(user ->
                user.getEmail().equals(request.email())));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldUpdatePasswordSuccessfully() {
        UpdateUserRequest request = new UpdateUserRequest(
                null,
                "new.password",
                null
        );
        User savedUser = newUser();
        ReflectionTestUtils.setField(savedUser, "id", id);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        when(passwordEncoder.encode(request.password())).thenReturn("new-" + encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.updateUser(id, request);

        verify(userRepository).findById(id);
        verify(userRepository).save(argThat(user ->
                user.getPassword().equals("new-encoded-password")));
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void shouldEnableUserSuccessfully() {
        User savedUser = newUser();
        ReflectionTestUtils.setField(savedUser, "id", id);
        savedUser.disable();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        assertFalse(savedUser.isActive());

        userService.enable(id);
        assertTrue(savedUser.isActive());

        verify(userRepository).findById(id);
        verify(userRepository).save(argThat(User::isActive));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldDisableUserSuccessfully() {
        User savedUser = newUser();
        ReflectionTestUtils.setField(savedUser, "id", id);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        assertTrue(savedUser.isActive());

        userService.disable(id);
        assertFalse(savedUser.isActive());

        verify(userRepository).findById(id);
        verify(userRepository).save(argThat(user ->
                !user.isActive()));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldReturnConflictWhenUserNameAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest(
                "userName",
                "password",
                "email@email.com"
        );

        when(userRepository.existsByUserNameIgnoreCase(request.userName())).thenReturn(true);

        assertThrows(UserNameAlreadyExistsException.class, () -> userService.createUser(request));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest(
                "userName",
                "password",
                "email@email.com"
        );

        when(userRepository.existsByUserNameIgnoreCase(request.userName())).thenReturn(false);
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.createUser(request));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() {
        UpdateUserRequest request = new UpdateUserRequest(
                "new userName",
                null,
                null
        );
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, request));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnConflictWhenUserNameAlreadyExistsOnAnotherUser() {
        UpdateUserRequest request = new UpdateUserRequest(
                "new userName",
                null,
                null
        );

        User savedUser = newUser();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        when(userRepository.existsByUserNameIgnoreCaseAndIdNot(request.userName(), id)).thenReturn(true);

        assertThrows(UserNameAlreadyExistsException.class, () -> userService.updateUser(id, request));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExistsOnAnotherUser() {
        UpdateUserRequest request = new UpdateUserRequest(
                null,
                null,
                "new.email@email.com"
        );

        User savedUser = newUser();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));
        when(userRepository.existsByEmailAndIdNot(request.email(), id)).thenReturn(true);

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.updateUser(id, request));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnConflictWhenNewAndOldPasswordAreIdentical() {
        UpdateUserRequest request = new UpdateUserRequest(
                null,
                "password",
                null
        );

        User savedUser = newUser();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));

      when(passwordEncoder.matches(request.password(), savedUser.getPassword())).thenReturn(true);
      assertThrows(PasswordConflictException.class, () -> userService.updateUser(id, request));
      verifyNoMoreInteractions(userRepository);
    }

    private User newUser() {
        return new User(
                "userName",
                "password",
                "email@email.com"
        );
    }
}
