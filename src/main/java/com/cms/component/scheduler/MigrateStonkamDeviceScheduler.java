package com.cms.component.scheduler;

import com.cms.serviceimpl.FetchDataServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MigrateStonkamDeviceScheduler {

    @Autowired
    private FetchDataServiceImpl fetchDataService;

    // excute after the application initialized 10 second later.
    // then trigger each 10 min.
    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 10000)
    private void fetchDeviceData() {
        this.fetchDataService.syncDeviceData();
    }
}