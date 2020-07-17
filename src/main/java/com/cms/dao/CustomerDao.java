package com.cms.dao;

import java.util.List;

import com.cms.model.CustomerModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerDao extends JpaRepository<CustomerModel, Integer>{
    @Query(value="select * from customer group by stk_user;",nativeQuery = true)
    public List<CustomerModel> findAllByOrderByStkUserAsc();
}