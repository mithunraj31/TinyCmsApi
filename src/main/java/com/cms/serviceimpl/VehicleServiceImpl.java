package com.cms.serviceimpl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dto.DailyOnlineDto;
import com.cms.dto.HourlyOnlineDto;
import com.cms.dto.VehicleDto;
import com.cms.http.VehicleHttp;
import com.cms.model.view.OnlineDevice;

@Service("VehicleServiceImpl")
public class VehicleServiceImpl {
	@Autowired
	private VehicleHttp vehicleHttp;

	@PersistenceContext
    private EntityManager entityManager;

	public List<VehicleDto> getAllVehiclesByUser(String user) {
		List<VehicleDto> vehicleList = new ArrayList<>();
		try {
			vehicleList = vehicleHttp.getAllVehicles(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicleList;
	}

	/**
	 * get number of online vehicle from event database table.
	 * @param user stk_user from kitting server 
	 * @return long number of online vehicle
	 */
	public long getOnlineVehicle(String user) {
		Query query = null;
		// user has not value will calculate all vehicle
		if (user != null && !user.isEmpty()){
			query = this.entityManager.createQuery("SELECT v FROM OnlineDevice v WHERE v.userName = :username", OnlineDevice.class);
			query.setParameter("username", user);
		} else {
			query = this.entityManager.createQuery("SELECT v FROM OnlineDevice v", OnlineDevice.class);
		}
		
		List<OnlineDevice> results = query.getResultList();

		return results.size();
	}

	/**
	 * get online vehicle by day range.
	 * @param days day's search range  
	 * @param user stk_user from kitting server 
	 * @return List<DailyOnlineDto> result object contians day and number of online vehicle each object
	 */
	public List<DailyOnlineDto> getDailyOnlineDevice(int days, String user) {
		StoredProcedureQuery query = 
			this.entityManager
			.createStoredProcedureQuery("get_daily_online");

		// register stored procedure parameter.
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

		// set parameter value
		query.setParameter(1, days);
		query.setParameter(2, user);
		List<Object[]> results = query.getResultList();
		List<DailyOnlineDto> dailyOnlineDtoList = new ArrayList<DailyOnlineDto>();

		// map result objects to class model
		results.stream().forEach((record) -> {
			Object day = record[0];
			Object online = record[1];
			dailyOnlineDtoList.add(
				new DailyOnlineDto(this.formatStringDateFormObject(day), 
				((BigInteger)online).longValue()));
		});
		
		return dailyOnlineDtoList;
	}

	/**
	 * get number of online vehicle by hour range in 24 hour
	 * @param user stk_user from kitting server 
	 * @return List<HourlyOnlineDto> result object contians day, hour, and number of online vehicle each object
	 */
	public List<HourlyOnlineDto> getHourlyOnlineDevice(String user) {
		StoredProcedureQuery query = 
			this.entityManager
			.createStoredProcedureQuery("get_one_day_hourly_online");

		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.setParameter(1, user);

		List<Object[]> results = query.getResultList();
		List<HourlyOnlineDto> hourlyOnlineDtoList = new ArrayList<HourlyOnlineDto>();
		results.stream().forEach((record) -> {
			Object day = record[0];
			Object hour = record[1];
			Object online = record[2];
			hourlyOnlineDtoList.add(new HourlyOnlineDto(
				this.formatStringDateFormObject(day), 
				(int) hour,
				((BigInteger)online).longValue()));
		});
		
		return hourlyOnlineDtoList;
	}

	/**
	 * cast date object to string in format yyyy-MM-dd
	 * @param dateObj Object class contain date value
	 * @return String if the object is string  will return string date 
	 * if not string will return empty string
	 */
	private String formatStringDateFormObject(Object dateObj) {
		
		if (dateObj instanceof java.util.Date) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			java.util.Date date = (java.util.Date) dateObj;

			return simpleDateFormat.format(date);
		}
		return "";
	}
}
