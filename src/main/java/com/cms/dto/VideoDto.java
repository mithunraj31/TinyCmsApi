package com.cms.dto;

public class VideoDto {

    private String videoUrl;

    private long noOfCamera;

    private long noOfVideo;

    private String videoId;

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getNoOfCamera() {
        return this.noOfCamera;
    }

    public void setNoOfCamera(long noOfCamera) {
        this.noOfCamera = noOfCamera;
    }

    public long getNoOfVideo() {
        return this.noOfVideo;
    }

    public void setNoOfVideo(long noOfVideo) {
        this.noOfVideo = noOfVideo;
    }
}