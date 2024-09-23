package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.UserStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserInfoResponse createUser(UserInfoRequest request) {
        validateEmail(request);

        userRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(user -> {throw new CustomException(String.format("User with email: %s already exists", request.getEmail()), HttpStatus.BAD_REQUEST);
                });

        User user = objectMapper.convertValue(request, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.CREATED);

        User save = userRepository.save(user);

        return objectMapper.convertValue(save, UserInfoResponse.class);
    }

    private void validateEmail(UserInfoRequest request) {
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new CustomException("Invalid email format", HttpStatus.BAD_REQUEST);
        }
    }

    public UserInfoResponse getUser(Long id) {
        return objectMapper.convertValue(getUserFromDB(id), UserInfoResponse.class);
    }

    public User getUserFromDB(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        User user = getUserFromDB(id);
        if (request.getEmail() == null) {
            user.setEmail(user.getEmail());
        } else {
            validateEmail(request);
            user.setEmail(request.getEmail());
        }
        user.setGender(request.getGender() == null ? user.getGender() : request.getGender());
        user.setFirstName(request.getFirstName() == null ? user.getFirstName() : request.getFirstName());
        user.setLastName(request.getLastName() == null ? user.getLastName() : request.getLastName());
        user.setMiddleName(request.getMiddleName() == null ? user.getMiddleName() : request.getMiddleName());
        user.setPassword(request.getPassword() == null ? user.getPassword() : request.getPassword());
        user.setAge(request.getAge() == null ? user.getAge() : request.getAge());


        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.UPDATED);

        User save = userRepository.save(user);

        return objectMapper.convertValue(save, UserInfoResponse.class);
    }

    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
        User user = getUserFromDB(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    public Page<UserInfoResponse> getAllUsers(Integer page, Integer sizePerPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, sizePerPage, sort, order);

        Page<User> all;
        if (filter == null) {
            all = userRepository.findAllNotDeleted(pageRequest, UserStatus.DELETED);
        } else {
            all = userRepository.findAllNotDeletedAndFiltered(pageRequest, UserStatus.DELETED, filter.toLowerCase());
        }

        List<UserInfoResponse> content = all.getContent().stream()
                .map(user -> objectMapper.convertValue(user, UserInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, all.getTotalElements());
    }

    public User updateUserData(User user) {
        return userRepository.save(user);
    }


}
