package com.cms.controller;

import java.util.Map;

import javax.validation.Valid;

import com.cms.model.NotifierModel;
import com.cms.serviceimpl.LambdaServiceImpl;
import com.cms.serviceimpl.RealTimeServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/lambda", produces = "application/json;charset=UTF-8")
public class LambdaController {
    @Autowired
    private LambdaServiceImpl lambdaService;

    @Autowired
    private RealTimeServiceImpl realtimeService;

    @PostMapping("/video")
    public ResponseEntity<?> newVideoUploadNotifier(@Valid @RequestBody final NotifierModel vunModel) {
        final Map<String, Object> data = this.lambdaService.uploadNewVideoNotifier(vunModel.getDeviceId(), vunModel.getEventId());
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<?> newEventNotifier(@Valid @RequestBody final NotifierModel eventModel) {
        final Boolean result = this.lambdaService.onEventOccur(eventModel.getEventId());
        if (result){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}