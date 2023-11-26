package com.aryan.javaminiproject.backend.concerthive.repositories;

import com.aryan.javaminiproject.backend.concerthive.models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    //Saving a user
    @Transactional
    @Modifying
    @Query("INSERT INTO User u (u.username, u.email, u.password) VALUES (:username, :email, :password)")
    int insertUser(@Param("username") String username,
                          @Param("email") String email,
                          @Param("password") String password
                          );

    @Query("select count(u.email) from User u where u.email=:email")
    int userExists(@Param("email") String email);

    @Query("select count(u.username) from User u where u.username=:username")
    int userExistsByUsername(@Param("username") String username);

    @Query("select u.password from User u where u.username=:username")
    String getPasswordByUsername(@Param("username") String username);

    //Fetch all data from table
    @Query("select u from User u where u.username=:username")
    User getAllData(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.username = :username, u.email = :email, u.password = :password WHERE u.id = :id")
    int updateUserById(@Param("id") int id, @Param("username") String username, @Param("email") String email, @Param("password") String password);
}
