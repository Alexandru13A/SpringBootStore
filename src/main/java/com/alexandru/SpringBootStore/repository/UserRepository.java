package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {





    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.email =: email WHERE u.userId =: userId")
    int updateUserEmail(Long id, String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName =: firstName WHERE u.userId =: userId")
    int updateUserFirstName(Long id, String firstName);

    @Modifying
    @Query("UPDATE User u SET u.lastName =: lastName WHERE u.userId =: userId")
    int updateUserLastName(Long id, String lastName);
}
