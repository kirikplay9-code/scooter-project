package com.scooter.scooterrental.user.enums.service;

import com.scooter.scooterrental.common.exception.*;
import com.scooter.scooterrental.user.enums.UserRole;
import com.scooter.scooterrental.user.enums.dto.UserLoginDto;
import com.scooter.scooterrental.user.enums.dto.UserRegistrationDto;
import com.scooter.scooterrental.user.enums.dto.UserResponseDto;
import com.scooter.scooterrental.user.enums.entity.Role;
import com.scooter.scooterrental.user.enums.entity.User;
import com.scooter.scooterrental.user.enums.repository.RoleRepository;
import com.scooter.scooterrental.user.enums.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    private String hashPassword(String raw) {
        return "hash_" + raw.hashCode();
    }

    @Transactional
    public UserResponseDto register(UserRegistrationDto dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new UserAlreadyExistsException("логин уже занят");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("уже есть такая почта");
        }

        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(hashPassword(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBalance(0.0);
        user.setActive(true);

        Role clientRole = roleRepository.findByName(UserRole.CLIENT)
                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
        user.setRole(clientRole);

        userRepository.save(user);
        return toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto login(UserLoginDto dto) {
        User user = userRepository.findByLoginAndActiveTrue(dto.getLogin())
                .orElseThrow(() -> new InvalidLoginException("неверный логин"));
        if (!user.getPassword().equals(hashPassword(dto.getPassword()))) {
            throw new InvalidPasswordException("неверный пароль");
        }
        return toResponseDto(user);
    }

    @Transactional
    public void topUpBalance(UUID userId, Double amount) {
        User user = getUserById(userId);
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(UUID id) {
        return toResponseDto(getUserById(id));
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    private UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBalance(user.getBalance());
        return dto;
    }
}