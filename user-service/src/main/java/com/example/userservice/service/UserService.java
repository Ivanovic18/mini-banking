package com.example.userservice.service;

import com.example.userservice.dto.LoginRequestDto;
import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.InvalidCredentialsException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.exception.UsernameAlreadyExistsException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public UserResponseDto getById(Long id) {
        return toResponseDto(findEntity(id));
    }

    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException("Korisnicko ime vec postoji: " + dto.getUsername());
        }
        User user = new User();
        user.setIme(dto.getIme());
        user.setPrezime(dto.getPrezime());
        user.setEmail(dto.getEmail());
        user.setTelefon(dto.getTelefon());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return toResponseDto(saved);
    }

    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = findEntity(id);
        if (!user.getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException("Korisnicko ime vec postoji: " + dto.getUsername());
        }
        user.setIme(dto.getIme());
        user.setPrezime(dto.getPrezime());
        user.setEmail(dto.getEmail());
        user.setTelefon(dto.getTelefon());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return toResponseDto(saved);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User findEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Korisnik nije pronadjen, id: " + id));
    }

    private UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getIme(),
                user.getPrezime(),
                user.getEmail(),
                user.getTelefon(),
                user.getUsername()
        );
    }

    public String login(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Pogresno korisnicko ime ili lozinka"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Pogresno korisnicko ime ili lozinka");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}

