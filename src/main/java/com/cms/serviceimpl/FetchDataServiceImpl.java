package com.cms.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cms.dao.CustomerDao;
import com.cms.dao.DeviceDao;
import com.cms.dto.CustomerDto;
import com.cms.dto.VehicleDto;
import com.cms.http.CustomerHttp;
import com.cms.http.VehicleHttp;
import com.cms.model.CustomerModel;
import com.cms.model.DeviceModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("FetchDataServiceImpl")
public class FetchDataServiceImpl {
    @Autowired
    private CustomerHttp customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private VehicleHttp vehicleService;

    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 1000)
    private void fetchCustomerData() {
        this.syncCustomerData();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 10000)
    private void fetchDeviceData() {
        this.syncDeviceData();
    }

    public Boolean syncCustomerData() {
        List<CustomerDto> customerList;
        try {
            customerList = customerService.getAllCustomers();
        } catch (IOException e1) {
            return false;
        }
        for (int i = 0; customerList.size() > i; i++) {
            final CustomerModel customer = new CustomerModel();
            customer.setId(customerList.get(i).getId());
            customer.setSid(customerList.get(i).getSid());
            customer.setStkUser(customerList.get(i).getStk_user());
            customer.setDescription(customerList.get(i).getDescription());
            try {
                customerDao.save(customer);

            } catch (final Exception e) {
                return false;
            }
        }
        return true;
    }

    public Boolean syncDeviceData() {
        final List<CustomerModel> customers = this.customerDao.findAllByOrderByStkUserAsc();
        for (int i = 0; customers.size() > i; i++) {
            List<VehicleDto> devices;
            try {
                devices = this.vehicleService.getAllVehicles(customers.get(i).getStkUser());
            } catch (IOException e1) {
                return false;
            }
            for (int j = 0; devices.size() > j; j++) {
                DeviceModel toSaveDevice = new DeviceModel();
                toSaveDevice.setActive(devices.get(j).getDetail().isIsActive());
                toSaveDevice.setChannelNumber(devices.get(j).getDetail().getChannelNumber());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createTime = LocalDateTime.parse(devices.get(j).getDetail().getCreateTime(), formatter);

                toSaveDevice.setCreateTime(createTime);
                toSaveDevice.setDeviceId(devices.get(j).getId().toString());
                toSaveDevice.setDeviceType(devices.get(j).getDetail().getDeviceType());
                toSaveDevice.setGroupName(devices.get(j).getDetail().getGroupName());
                toSaveDevice.setNetType(devices.get(j).getDetail().getNetType());
                toSaveDevice.setPlateNumber(devices.get(j).getDetail().getPlateNumber());
                toSaveDevice.setScanCode(devices.get(j).getDetail().getScanCode());
                toSaveDevice.setStkUser(customers.get(i).getStkUser());
                toSaveDevice.setTcpServerAddr(devices.get(j).getDetail().getTcpServerAddr());
                toSaveDevice.setTcpStreamOutPort(devices.get(j).getDetail().getTcpStreamOutPort());
                toSaveDevice.setUdpServerAddr(devices.get(j).getDetail().getUdpServerAddr());
                toSaveDevice.setUdpStreamOutPort(devices.get(j).getDetail().getUdpStreamOutPort());
                toSaveDevice.setUpdate_time(LocalDateTime.now());

                try {

                    this.deviceDao.save(toSaveDevice);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return true;
    }
}