package com.cms.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cms.dao.CustomerDao;
import com.cms.dao.DeviceDao;
import com.cms.dto.CustomerDto;
import com.cms.dto.VehicleDto;
import com.cms.http.CustomerHttp;
import com.cms.http.VehicleHttp;
import com.cms.model.CustomerModel;
import com.cms.model.DeviceModel;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Boolean syncCustomerData() {
        List<CustomerDto> customerList;
        try {
            customerList = customerService.getAllCustomers();
        } catch (IOException e1) {
            return false;
        }

        final List<CustomerModel> existingList = customerDao.findAll();

        List<Integer> customerIdList = customerList.stream()
                                        .map(x -> x.getId())
                                        .collect(Collectors.toList());

        List<CustomerModel> deletingList = existingList.stream()
                                            .filter(x -> !customerIdList.contains(x.getId()))
                                            .collect(Collectors.toList());

        this.deleteCustomers(deletingList);

        final List<CustomerModel> customerModels = new ArrayList<>();
        for (CustomerDto customerDto : customerList) {
            final CustomerModel customer = new CustomerModel();
            customer.setId(customerDto.getId());
            customer.setSid(customerDto.getSid());
            customer.setStkUser(customerDto.getStk_user());
            customer.setDescription(customerDto.getDescription());
            customerModels.add(customer);
        }
        try {
            customerDao.saveAll(customerModels);

        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    public Boolean syncDeviceData() {
        final List<CustomerModel> customers = this.customerDao.findAllByOrderByStkUserAsc();

        List<DeviceModel> toSaveDevices = new ArrayList<>();
        for (CustomerModel customer : customers) {
            List<VehicleDto> devices;
            try {
                devices = this.vehicleService.getAllVehicles(customer.getStkUser());
            } catch (IOException e1) {
                return false;
            }

            final List<DeviceModel> existingList = this.deviceDao.findAll();

            List<String> deviceIdList = devices.stream()
                                            .map(x -> x.getId() + "")
                                            .collect(Collectors.toList());

            List<DeviceModel> deletingList = existingList.stream()
                                            .filter(x -> !deviceIdList.contains(x.getDeviceId()))
                                            .collect(Collectors.toList());

            this.deleteDevices(deletingList);

            for (VehicleDto device : devices) {
                DeviceModel toSaveDevice = new DeviceModel();
                toSaveDevice.setActive(device.getDetail().isIsActive());
                toSaveDevice.setChannelNumber(device.getDetail().getChannelNumber());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createTime = LocalDateTime.parse(device.getDetail().getCreateTime(), formatter);

                toSaveDevice.setCreateTime(createTime);
                toSaveDevice.setDeviceId(device.getId().toString());
                toSaveDevice.setDeviceType(device.getDetail().getDeviceType());
                toSaveDevice.setGroupName(device.getDetail().getGroupName());
                toSaveDevice.setNetType(device.getDetail().getNetType());
                toSaveDevice.setPlateNumber(device.getDetail().getPlateNumber());
                toSaveDevice.setScanCode(device.getDetail().getScanCode());
                toSaveDevice.setStkUser(customer.getStkUser());
                toSaveDevice.setTcpServerAddr(device.getDetail().getTcpServerAddr());
                toSaveDevice.setTcpStreamOutPort(device.getDetail().getTcpStreamOutPort());
                toSaveDevice.setUdpServerAddr(device.getDetail().getUdpServerAddr());
                toSaveDevice.setUdpStreamOutPort(device.getDetail().getUdpStreamOutPort());
                toSaveDevice.setUpdate_time(LocalDateTime.now());
                toSaveDevices.add(toSaveDevice);
            }
        }

        try {
            this.deviceDao.saveAll(toSaveDevices);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void deleteCustomers(List<CustomerModel> entities) {
        if (entities != null && entities.size() > 0) {
            this.customerDao.deleteAll(entities);
        }
    }

    private void deleteDevices(List<DeviceModel> entities) {
        if (entities != null && entities.size() > 0) {
            this.deviceDao.deleteAll(entities);
        }
    }
}