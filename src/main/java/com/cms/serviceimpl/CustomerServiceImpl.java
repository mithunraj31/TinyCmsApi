package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.CustomerDao;
import com.cms.dto.CustomerDto;
import com.cms.dto.CustomerStatusDto;
import com.cms.http.CustomerHttp;
import com.cms.model.CustomerModel;

@Service("CustomerServiceImpl")
public class CustomerServiceImpl {
	@Autowired
	private CustomerHttp customerHttp;

	@Autowired
	private CustomerDao customerDao;

	public List<CustomerDto> getAllCustomers() {

		List<CustomerDto> customers = new ArrayList<CustomerDto>();
		List<CustomerModel> customersModelList = customerDao.findAllByOrderByStkUserAsc();

		// Map Customer model to Customer Dto
		for (int i = 0; customersModelList.size() > i; i++) {
			// FIlter customers with stk_user
			if (!customersModelList.get(i).getStkUser().isEmpty()) {
				CustomerDto c = new CustomerDto();
				c.setId(customersModelList.get(i).getId());
				c.setSid(customersModelList.get(i).getSid());
				c.setStk_user(customersModelList.get(i).getStkUser());
				c.setDescription(customersModelList.get(i).getDescription());

				customers.add(c);
			}
		}
		return customers;
	}

	public List<CustomerStatusDto> getCustomersStatus() {
		List<CustomerStatusDto> customersStatus = new ArrayList<CustomerStatusDto>();
		List<CustomerDto> stk_users = this.getAllCustomers();
		customersStatus = this.customerHttp.getCustomersStatusByid(stk_users);

		return customersStatus;
	}
}
