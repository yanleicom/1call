package com.yanlei.springboot.controller;

import com.yanlei.springboot.service.MatterService.MatterService;
import com.yanlei.springboot.service.SchemeService.SchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: x
 * @Date: Created in 12:24 2018/11/21
 */
@RestController
public class MyJob {

    private Logger logger = LoggerFactory.getLogger(MyJob.class);

    @Autowired
    SchemeService schemeService;
    @Autowired
    MatterService matterService;

    /**
     * @Author: x
     * @Date: Created in 12:25 2018/11/21
     * 定时执行任务 匹配主动办方案定时执行重复方案(按周和按月匹配符合条件方案定时推送工单)
     * 每月一次 几日  查询执行时间不为空,
     * 每周一次 星期几
     * 0 0 10 * * 1/5  从星期一到星期日每天的早上十点
     * 0 0 10 * * ?  //每天早上十点
     */
//    @Scheduled(cron = "0 * * * * MON-FRI") //定时的 测 试 cron表达式

    /**
    * @Author: x
    * @Date: Created in 14:09 2018/11/21
     * 周定时任务,匹配针对所有人群
    */

//    @GetMapping("/myWeekJobAll")
    @Scheduled(cron = "0 0 10 * * ?")
    public void myWeekJobAll(){
        int result =  schemeService.myWeekJobAll();
        logger.info("周定时任务针对所有人群执行成功 ---->>> 执行次数:"+result);
    }

    /**
    * @Author: x
    * @Date: Created in 15:00 2018/11/21
     * 月定时任务,匹配针对所有人群
    */
//    @GetMapping("/myMonthJobAll")
    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(cron = "0 * * * * MON-FRI")
    public void myMonthJobAll(){
        int result =  schemeService.myMonthJobAll();
        logger.info("月定时任务针对所有人群执行成功 ---->>> 执行次数:"+result);
    }

    /**
    * @Author: x
    * @Date: Created in 15:16 2018/11/21
     * 周定时任务,匹配针对新增人群
    */
//    @GetMapping("/myWeekJobAdd")
    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(cron = "0 * * * * MON-FRI")
    public void myWeekJobAdd(){
        String result =  schemeService.myWeekJobAdd();
        logger.info(result);
    }

    /**
    * @Author: x
    * @Date: Created in 10:24 2018/11/22
     * 月定时任务,匹配针对新增人群
    */

//    @GetMapping("/myMonthJobAdd")
    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(cron = "0 * * * * MON-FRI")
    public void myMonthJobAdd(){
        String result =  schemeService.myMonthJobAdd();
        logger.info(result);
    }

}
