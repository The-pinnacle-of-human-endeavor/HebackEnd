package com.healcare.app.pojo;
import org.springframework.boot.autoconfigure.domain.EntityScan;

public class Video {
    private String videoName;
    private String videoId;
    private String videoSrc;
    private String videoIndex;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
