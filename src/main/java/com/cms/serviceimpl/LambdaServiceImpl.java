package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.cms.dao.CameraDao;
import com.cms.dao.DeviceDao;
import com.cms.dao.VideoDao;
import com.cms.exceptions.GetCamerasException;
import com.cms.exceptions.GetVideosException;
import com.cms.exceptions.VideoNotFullfilledException;
import com.cms.http.LambdaHttp;
import com.cms.model.CameraModel;
import com.cms.model.DeviceModel;
import com.cms.model.VideoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("LambdaServiceImpl")
public class LambdaServiceImpl {
    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private CameraDao cameraDao;

    @Autowired
    private LambdaHttp lambdaHttp;

    @Value("${vt.bucket.name}")
    private String bucketName;

    // This method will check wether the required no of videos are uploaded to the
    // db
    // and if uploaded this method will notify a Lambda function to Convert multiple
    // videos in to new video
    // then video_converted table will be inserted the new converted url
    public Map<String, Object> uploadNewVideoNotifier(String deviceId, String eventId) {
        Map<String, Object> response = new HashMap<>();
        // Check device camera count
        DeviceModel device = this.deviceDao.getDeviceById(deviceId);
        // Check whether the device is available
        if (device.getDeviceId() == null) {
            response.put("massage", "Device Not Found");
            response.put("code", 404);
            return response;
        }
        // Get uploaded videos
        List<VideoModel> videos = new ArrayList<VideoModel>();
        try {

            videos = this.videoDao.getVideoByEventId(eventId);
        } catch (Exception e) {
            System.out.println(e);
            response.put("massage", "Something went wrong");
            response.put("code", 500);
            return response;
        }

        // Get camera Details
        List<CameraModel> cameras = new ArrayList<CameraModel>();
        try {
            cameras = this.cameraDao.getCamerasByDeviceId(deviceId);
        } catch (Exception e) {
            System.out.println(e);
            response.put("massage", "Something went wrong on camera details");
            response.put("code", 500);
            return response;
        }

        if (cameras.size() > 0 && cameras.size() == videos.size()) {
            // need to notify the lambda to convert video
            return this.startConversionLambda(videos, cameras);
        } else {
            response.put("massage", "Not yet fulfilled the requirement to start the conversion");
            response.put("requiredVideos", cameras.size());
            response.put("currentVideos", videos.size());
            response.put("code", 0);
            return response;
        }

    }

    private Map<String, Object> startConversionLambda(List<VideoModel> videos, List<CameraModel> cameras) {
        Map<String, Object> lambdaRequest = new HashMap<>();
        Map<String, Object> bucket = new HashMap<>();
        Map<String, Object> video = new HashMap<>();

        String filename = videos.get(0).getUrl();
        filename = filename.split("/")[6];
        String path = videos.get(0).getUrl();
        path = path.replace(filename, "");

        bucket.put("name", this.bucketName);
        bucket.put("path", path);
        lambdaRequest.put("bucket", bucket);

        // this will change in the future
        video.put("cols", "auto");

        List<Map<String, Object>> files = new ArrayList<>();
        for (int i = 0; cameras.size() > i; i++) {
            Map<String, Object> f = new HashMap<>();
            int rotation = cameras.get(i).getRotation();
            String name = this.findVideo(videos, cameras.get(i).getCh());
            if (name==null) {
                continue;
            }
            f.put("rotate", rotation);
            f.put("name", name);
            files.add(f);
        }
        video.put("files", files);

        lambdaRequest.put("video", video);

        // execute lambda
        try {
            String res = this.lambdaHttp.startVideoConversion(lambdaRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("massage", "Convertion Started");
            response.put("code", 200);
            response.put("lambda", lambdaRequest);
            response.put("response",res );
            return response;
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("massage", "Somthing went wrong when starting lambda");
            response.put("code", 500);
            response.put("lambda", lambdaRequest);
            return response;
        }

    }

    private String findVideo(List<VideoModel> videos, String ch) {

        for (int i = 0; videos.size() > i; i++) {
            String f = videos.get(i).getUrl();
            f = f.split("/")[6];

            String ch2 = f.split("_")[9];
            if (ch.equals(ch2)) {
                return f;
            }
        }
        return null;
    }

    public Map<String, Object>  uploadNewVideoNotifierV2(String deviceId, String eventId) {
        // Check device camera count
        DeviceModel device = this.deviceDao.getDeviceById(deviceId);
        // Check whether the device is available
        if (device.getDeviceId() == null) {
            throw new NoSuchElementException();
        }

        // Get uploaded videos
        List<VideoModel> videos = new ArrayList<VideoModel>();
        try {
            videos = this.videoDao.getVideoByEventId(eventId);
        } catch (Exception e) {
            throw new GetVideosException();
        }

         // Get camera Details
         List<CameraModel> cameras = new ArrayList<CameraModel>();
         try {
             cameras = this.cameraDao.getCamerasByDeviceId(deviceId);
         } catch (Exception e) {
             throw new GetCamerasException();
         }


         // Get uploaded videos
        if (cameras.size() > 0 && cameras.size() == videos.size()) {
            // need to notify the lambda to convert video
            return this.startConversionLambdaV2(videos, cameras);
        } else {
            throw new VideoNotFullfilledException();
        }
    }

    private Map<String, Object> startConversionLambdaV2(List<VideoModel> videos, List<CameraModel> cameras) {
        Map<String, Object> lambdaRequest = new HashMap<>();
        Map<String, Object> bucket = new HashMap<>();
        Map<String, Object> video = new HashMap<>();


        String videoUrl = videos.get(0).getUrl();
        String path = videoUrl.substring(0,  videoUrl.lastIndexOf(java.io.File.separator) );

        bucket.put("name", this.bucketName);
        bucket.put("path", path);
        lambdaRequest.put("bucket", bucket);

        // this will change in the future
        video.put("cols", "auto");

        List<Map<String, Object>> files = new ArrayList<>();
        for (int i = 0; cameras.size() > i; i++) {
            Map<String, Object> f = new HashMap<>();
            int rotation = cameras.get(i).getRotation();
            String name = this.findVideo(videos, cameras.get(i).getCh());
            if (name == null || name.isEmpty()) {
                continue;
            }
            f.put("rotate", rotation);
            f.put("name", name);
            files.add(f);
        }
        video.put("files", files);

        lambdaRequest.put("video", video);

        // execute lambda
        try {
            String res = this.lambdaHttp.startVideoConversion(lambdaRequest);
            Map<String, Object> result = new HashMap<>();
            result.put("lambda", lambdaRequest);
            result.put("response", res);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("lambda", lambdaRequest);
            return result;
        }

    }
}