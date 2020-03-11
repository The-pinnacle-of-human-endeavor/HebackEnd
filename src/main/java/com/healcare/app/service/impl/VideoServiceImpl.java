package com.healcare.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.healcare.app.mapper.VideoMapper;
import com.healcare.app.pojo.Video;
import com.healcare.app.service.VideoService;

import javax.annotation.Resource;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper VideoMapper;

    //这才是重点 主要看实现部分
    //返回对应 结构对应
    @Override
    public Video findVideoById(Integer sortId) {
        System.out.println("return VideoMapper.findVideoById(1):"+VideoMapper.findVideoById(sortId));
        return VideoMapper.findVideoById(sortId);
    }

    @Override
    public int insertVideo(Video video){
        return VideoMapper.insertVideo(video);
    }

    @Override
    public int querySize(){
        return VideoMapper.querySize();
    };
}
