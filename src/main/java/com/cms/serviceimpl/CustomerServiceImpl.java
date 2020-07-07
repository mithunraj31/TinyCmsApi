package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dto.CustomerDto;
import com.cms.http.CustomerHttp;

@Service("CustomerServiceImpl")
public class CustomerServiceImpl {
	@Autowired
	private CustomerHttp customerHttp;

	public List<CustomerDto> getAllCustomers() {

		List<CustomerDto> customers = new ArrayList<CustomerDto>();
		try {
			customers = customerHttp.getAllCustomers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// filter customers with stk_user
		List<CustomerDto> filteredCustomers = new ArrayList<CustomerDto>();
		System.out.println(customers.size());
		for (int i = 0; i < customers.size(); i++) {
			CustomerDto c = customers.get(i);
			if (c.getStk_user() != null && c.getStk_user() != "null" && c.getStk_user() != "") {
				filteredCustomers.add(c);
			}

		}
		return filteredCustomers;
	}
}
