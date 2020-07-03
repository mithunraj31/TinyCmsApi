package com.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cms.model.UserEntityModel;

@Repository
public interface UserDao extends JpaRepository<UserEntityModel, Integer> {
    UserEntityModel findByEmail(String email);

    @Transactional
	@Modifying
    @Query(value="INSERT INTO `user_role` (`user_userid`,`role_roleid`)VALUES(?1,?2) ", nativeQuery = true)
	public void addRoleToUser(int userId, int roleId);
}

