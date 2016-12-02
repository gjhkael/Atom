package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 张峻滔
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
    
    @Query("select username from User where id = :id")
    String getUsernameById(@Param("id") Long id);
}
