package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.status <>:status")
    Page<User> findAllNotDeleted(Pageable pageRequest, @Param("status") UserStatus userStatus);
    @Query("select u from User u where u.status <>:status and (lower(u.firstName) like %:filter% or lower(u.lastName) like %:filter% or lower(u.middleName) like %:filter%)")
    Page<User> findAllNotDeletedAndFiltered(Pageable pageRequest, @Param("status") UserStatus userStatus, String filter);

    Optional<User> findByEmailIgnoreCase(String email);
}
