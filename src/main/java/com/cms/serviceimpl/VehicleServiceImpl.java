package com.cms.serviceimpl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.DeviceDao;
import com.cms.dto.DailyOnlineDto;
import com.cms.dto.HourlyOnlineDto;
import com.cms.dto.VehicleDto;
import com.cms.http.VehicleHttp;
import com.cms.model.view.OnlineDevice;

@Service("VehicleServiceImpl")
public class VehicleServiceImpl {
	@Autowired
	private VehicleHttp vehicleHttp;

	@Autowired
	private DeviceDao deviceDao;

	@PersistenceContext
    private EntityManager entityManager;

	public List<VehicleDto> getAllVehiclesByUser(final String user) {
		List<VehicleDto> vehicleList = new ArrayList<>();
		try {
			vehicleList = vehicleHttp.getAllVehicles(user);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicleList;
	}

	/**
	 * get number of online vehicle from event database table.
	 * 
	 * @param user stk_user from kitting server
	 * @return long number of online vehicle
	 */
	public long getOnlineVehicle(final String user) {
		Query query = null;
		// user has not value will calculate all vehicle
		if (user != null && !user.isEmpty()) {
			query = this.entityManager.createQuery("SELECT v FROM OnlineDevice v WHERE v.userName = :username",
					OnlineDevice.class);
			query.setParameter("username", user);
		} else {
			query = this.entityManager.createQuery("SELECT v FROM OnlineDevice v", OnlineDevice.class);
		}

		final List<OnlineDevice> results = query.getResultList();

		return results.size();
	}

	/**
	 * get online vehicle by day range.
	 * 
	 * @param days day's search range
	 * @param user stk_user from kitting server
	 * @return List<DailyOnlineDto> result object contians day and number of online
	 *         vehicle each object
	 */
	public List<DailyOnlineDto> getDailyOnlineDevice(final int days, final String user) {
		final StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("get_daily_online");

		// register stored procedure parameter.
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

		// set parameter value
		query.setParameter(1, days);
		query.setParameter(2, user);
		final List<Object[]> results = query.getResultList();
		final List<DailyOnlineDto> dailyOnlineDtoList = new ArrayList<DailyOnlineDto>();

		// map result objects to class model
		results.stream().forEach((record) -> {
			final Object day = record[0];
			final Object online = record[1];
			dailyOnlineDtoList
					.add(new DailyOnlineDto(this.formatStringDateFormObject(day), ((BigInteger) online).longValue()));
		});

		return dailyOnlineDtoList;
	}

	/**
	 * get number of online vehicle by hour range in 24 hour
	 * 
	 * @param user stk_user from kitting server
	 * @return List<HourlyOnlineDto> result object contians day, hour, and number of
	 *         online vehicle each object
	 */
	public List<HourlyOnlineDto> getHourlyOnlineDevice(final String user) {
		final StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("get_one_day_hourly_online");

		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.setParameter(1, user);

		final List<Object[]> results = query.getResultList();
		final List<HourlyOnlineDto> hourlyOnlineDtoList = new ArrayList<HourlyOnlineDto>();
		results.stream().forEach((record) -> {
			final Object day = record[0];
			final Object hour = record[1];
			final Object online = record[2];
			hourlyOnlineDtoList.add(new HourlyOnlineDto(this.formatStringDateFormObject(day),
					((BigInteger) hour).intValue(), ((BigInteger) online).longValue()));
		});

		return hourlyOnlineDtoList;
	}

	/**
	 * cast date object to string in format yyyy-MM-dd
	 * 
	 * @param dateObj Object class contain date value
	 * @return String if the object is string will return string date if not string
	 *         will return empty string
	 */
	private String formatStringDateFormObject(final Object dateObj) {

		if (dateObj instanceof java.util.Date) {
			final String pattern = "yyyy-MM-dd";
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			final java.util.Date date = (java.util.Date) dateObj;

			return simpleDateFormat.format(date);
		}
		return "";
	}

	// get vehicle information by id
	public VehicleDto getVehicleById(final long vehicleId, final String user) {
		// get all vehicle from stonkam API
		final List<VehicleDto> vehicleList = this.getAllVehiclesByUser(user);
		if (vehicleList != null && vehicleList.size() > 0
				&& vehicleList.stream().anyMatch(x -> x.getId() == vehicleId)) {
			// return vehicle information is contain device id match with vehicleId
			return vehicleList.stream().filter(x -> x.getId() == vehicleId).findFirst().get();
		}

		return null;

	}

	public long getTotalVehicleCount(final String user) {
		if (user != null && !user.isEmpty()) {
			return this.deviceDao.getCountByUser(user);
		} else {
			return this.deviceDao.count();
		}
	}
}
