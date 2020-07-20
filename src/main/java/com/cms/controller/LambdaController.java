package com.cms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.cms.exceptions.GetCamerasException;
import com.cms.exceptions.GetVideosException;
import com.cms.exceptions.VideoNotFullfilledException;
import com.cms.model.VideoUploadNotifierModel;
import com.cms.serviceimpl.LambdaServiceImpl;

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

    @PostMapping("/video")
    public ResponseEntity<?> newVideoUploadNotifier(@Valid @RequestBody VideoUploadNotifierModel vunModel) {
        Map<String, Object> data = this.lambdaService.uploadNewVideoNotifier(vunModel.getDeviceId(), vunModel.getEventId());
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

    @PostMapping("/video/v2")
    public ResponseEntity<?> newVideoUploadNotifierV2(@Valid @RequestBody VideoUploadNotifierModel vunModel) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = this.lambdaService.uploadNewVideoNotifierV2(vunModel.getDeviceId(), vunModel.getEventId());
            if (!result.containsKey("response")) {
                result.put("message", "Somthing went wrong when starting lambda");
                return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            result.put("message", "Convertion Started");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            response.put("message", "Device Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (GetVideosException ex) {
            response.put("message", "Something went wrong on get videos");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (GetCamerasException ex) {
            response.put("message", "Something went wrong on get camera");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (VideoNotFullfilledException ex) {
            response.put("message", "Not yet fulfilled the requirement to start the conversion");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}