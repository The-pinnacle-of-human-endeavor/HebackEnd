package com.healcare.app.mapper;

import org.apache.ibatis.annotations.*;

import com.healcare.app.pojo.Video;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Repository("VideoMapper")
public interface VideoMapper {
    //操作 结构 调用
    //{}用以设置对应数据
    @Select("SELECT VIDEO_ID, VIDEO_NAME, VIDEO_SRC, sort FROM ( SELECT d.*,(@rowno:=@rowno+1) as sort FROM ( SELECT a.VIDEO_ID,a.VIDEO_NAME,a.VIDEO_SRC FROM video a ORDER BY a.VIDEO_ID DESC) d,(select (@rowno:=0)) c) e where e.sort = #{sortId}")
    @Results(id="videoMap", value={
            @Result(column="video_id", property="videoId", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="video_name", property="videoName", jdbcType=JdbcType.VARCHAR),
            @Result(column="video_src", property="videoSrc", jdbcType=JdbcType.VARCHAR),
            @Result(column="video_index", property="videoIndex", jdbcType=JdbcType.VARCHAR)
    })
    public Video findVideoById(Integer sortId);

    //将变量值上挂到参数
    //直接不用映射
    @Select("select count(0) from video")
    public int querySize();

    @Insert("insert into video (VIDEO_ID, VIDEO_NAME, VIDEO_SRC) VALUES (#{videoId},#{videoName},#{videoSrc})")
    int insertVideo(Video video);
}
