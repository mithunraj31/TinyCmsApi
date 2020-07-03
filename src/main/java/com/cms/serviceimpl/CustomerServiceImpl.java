package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cms.HttpModel.Customer;
import com.cms.http.CustomerHttp;

@Service("CustomerServiceImpl")
public class CustomerServiceImpl {
	@Autowired
	private CustomerHttp customerHttp;

	public List<Customer> getAllCustomers() {

		List<Customer> customers = new ArrayList<Customer>();
		try {
			customers = customerHttp.getAllCustomers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// filter customers with stk_user
		List<Customer> filteredCustomers = new ArrayList<Customer>();
		System.out.println(customers.size());
		for (int i = 0; i < customers.size(); i++) {
			Customer c = customers.get(i);
			if (c.stk_user != null && c.stk_user != "null" && c.stk_user != "") {
				filteredCustomers.add(c);
			}

		}
		return filteredCustomers;
	}
}
