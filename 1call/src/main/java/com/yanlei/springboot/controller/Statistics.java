package com.yanlei.springboot.controller;

import com.yanlei.springboot.service.StatisticsService.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: x
 * @Date: Created in 16:12 2019/1/8
 * 1call主动办统计分析模块
 */
@RestController
public class Statistics {

    @Autowired
    StatisticsService statisticsService;

    /**
    * @Author: x
    * @Date: Created in 16:15 2019/1/8
     * 统计主动办办理人次 主动办方案数量 主动办事项人数 主动办事项数量
    */
    @GetMapping("/getPersonCount")
    public String PersonCount(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return statisticsService.getPersonCount();
    }

    /**
    * @Author: x
    * @Date: Created in 9:38 2019/1/9
     *  区域2：最新动态。按照人员办结时间倒序排序，根据页面尺寸最多显示6-8条。
    */
    @GetMapping("/getTrends")
    public String trends(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return  statisticsService.getTrends();
    }

    /**
    * @Author: x
    * @Date: Created in 10:47 2019/1/9
     * 区域3：主动办人次趋势。统计每月的所有方案人群数量
    */
    @GetMapping("/getSchemePersonCount")
    public String SchemePersonCount(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return statisticsService.getSchemePersonCount();
    }

    /**
    * @Author: x
    * @Date: Created in 11:54 2019/1/9
     * 区域4：方案办理状态。按照未通知、已通知、已办结的方案状态，
     * 对主动办方案数量进行统计，通过饼图展现其占比及数量关系。
    */
    @GetMapping("/getSchemeStart")
    public String SchemeStart(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String result = statisticsService.getSchemeStart();
        return result;
    }

    /**
    * @Author: x
    * @Date: Created in 14:41 2019/1/9
     * 区域5：事项主动办人数分析。横坐标轴为事项名称、纵坐标轴为人员数量。
    */
    @GetMapping("/getStatisticsMatterPerson")
    public String MatterPerson(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String result = statisticsService.getMatterPerson();
        return result;
    }
}
