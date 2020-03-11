package com.healcare.app.service;

import java.util.List;

import com.healcare.app.pojo.Video;

public interface VideoService {
    //结构一致 参数一致
    public Video findVideoById(Integer sortId);
    public int querySize();
    public int insertVideo(Video video);
}