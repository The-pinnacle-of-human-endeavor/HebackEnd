package com.healcare.app.controller;

import com.healcare.app.pojo.Video;
import com.healcare.app.service.impl.VideoServiceImpl;
import com.mysql.cj.protocol.ResultBuilder;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healcare.app.service.VideoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
public class VideoController {
    @Resource
    VideoService videoService;

    //上传文件

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(HttpServletRequest req, HttpServletResponse res)
    {
        MultipartFile mFile = ((DefaultMultipartHttpServletRequest) req).getMultiFileMap().get("dbFile").get(0);
        if (isEmpty(mFile) || mFile.getSize() <= 0)
        {
            System.out.println("接收到的文件为空");
        }
        try
        {
            String path = "C://";
            File tempFile = new File(path + mFile.getOriginalFilename());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping("/queryVideo")
    @ResponseBody
    //不要重名
    public Video QueryVideo(@RequestParam("sortId") Integer sortId) {
        //过程打印
        return videoService.findVideoById(sortId);
    }

    @RequestMapping("/querySize")
    @ResponseBody
    //不要重名
    public int QuerySize() {
        //过程打印
        return videoService.querySize();
    }

    @RequestMapping("/insertVideo")
    @ResponseBody
    //按照json参数名进行获取
    //然后把这个值赋予后面的值
    //请求针对外层内部直接调用服务
    public int InsertVideo(@RequestParam("video") Video video) {
        //页面耦合层
        return videoService.insertVideo(video);
    }

    @RequestMapping(value = "/uploadVideo")
    @ResponseBody
    //获取前端参数 文件类型的请求
    public Map<String,String> savaVideo(@RequestParam("file") MultipartFile file)
            throws IllegalStateException {
        Map<String,String> resultMap = new HashMap<>();
        try{
            //获取文件后缀
//            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
//                    .toLowerCase();
//            //获取视频名称
//            String videoName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));

            //获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
            Date date = new Date();// 获取当前时间
            System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
            String videoId = sdf.format(date);

            String videoName = file.getOriginalFilename().replaceAll(" ","");
            //忽略大小写，只保留数字和字母和点
            String newVideoName = (videoId+videoName).toLowerCase().replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,。？“”]+", "");

            //重构文件名称
//            String newVideoName = videoName+videoId+"."+fileExt;
            //忽略大小写，只保留数字和字母和点

            //保存视频信息
            //需要双杠进行转义
            String videoSrc = "/app/healcare/dist/appstatic/video/"+newVideoName;
            Video video = new Video();
            video.setVideoId(videoId);
            video.setVideoName(newVideoName);
            video.setVideoSrc(videoSrc);
            System.out.println("video:"+video);
            videoService.insertVideo(video);

            //保存视频
            File fileSave = new File("/app/healcare/dist/appstatic/video/", newVideoName);
            file.transferTo(fileSave);
            resultMap.put("resCode","1");
            resultMap.put("webShowPath","" + newVideoName);

            return  resultMap;

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("resCode","0");
            return  resultMap ;
        }
    }

}