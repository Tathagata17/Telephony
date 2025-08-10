package com.telephony.AuthService.repo;

import com.telephony.AuthService.entity.TelephonyUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthRepo extends JpaRepository<TelephonyUser,String> {

    @Query("SELECT u.password FROM TelephonyUser u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE TelephonyUser u SET u.password = :password WHERE u.email = :email")
    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}
