package com.cms.component.scheduler;

import com.cms.serviceimpl.FetchDataServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MigrateKittingUserScheduler {
    @Autowired
    private FetchDataServiceImpl fetchDataService;
    
    // excute after the application initialized 1 second later.
    // then trigger each 10 min.
    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 1000)
    private void fetchCustomerData() {
        this.fetchDataService.syncCustomerData();
    }
}