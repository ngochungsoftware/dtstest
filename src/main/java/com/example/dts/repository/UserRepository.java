package com.example.dts.repository;

import com.example.dts.entity.User;
import com.example.dts.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndDeletedFalse(String username);

    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByUsernameOrEmailAndDeletedFalse(String username, String email);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllActive();

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    Page<User> findAllActive(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.status = :status AND u.deleted = false")
    List<User> findByStatusAndDeletedFalse(@Param("status") UserStatus status);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.deleted = false")
    List<User> findByRoleAndDeletedFalse(@Param("role") String role);

    @Query("SELECT u FROM User u WHERE (u.name LIKE %:searchTerm% OR u.username LIKE %:searchTerm%) AND u.deleted = false")
    Page<User> searchUsers(@Param("searchTerm") String searchTerm, Pageable pageable);

    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByEmailAndDeletedFalse(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.deleted = false")
    long countActiveUsers();

    @Query("SELECT u FROM User u WHERE u.deleted = true")
    List<User> findAllDeleted();


}
