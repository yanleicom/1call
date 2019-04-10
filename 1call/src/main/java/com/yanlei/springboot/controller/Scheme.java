package com.yanlei.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.service.MatterService.MatterService;
import com.yanlei.springboot.service.SchemeService.SchemeService;
import com.yanlei.springboot.service.StatisticsService.StatisticsService;
import com.yanlei.springboot.util.CookieUtil;
import com.yanlei.springboot.util.HttpDataUtil;
import com.yanlei.springboot.util.JsonMethod;
import com.yanlei.springboot.util.PageHelperUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: x
 * @Date: Created in 17:28 2018/11/13
 */
@RestController
public class Scheme {

    private Logger logger = LoggerFactory.getLogger(Scheme.class);

    @Autowired
    SchemeService schemeService;
    @Autowired
    MatterService matterService;
    @Autowired
    StatisticsService statisticsService;

    /**
     * @Author: x
     * @Date: Created in 10:25 2018/11/14
     * * 在主动办方案中新建方案,搜索事项名称
     */
    @PostMapping("/findMatterName")
    public String findMatterName(ActiveMatter activeMatter,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (activeMatter.getMatter() == null)return "请输入事项名称";
        if (activeMatter.getStreet() == null) activeMatter.setStreet("武林"); //默认武林
        List<ActiveMatter> list = schemeService.findMatterName(activeMatter);
        if (list.size()>0 && list!=null){
            return JSON.toJSONString(list);
        }return "无对应事项,请先新建事项";
    }

    /**
     * @Author: x
     * @Date: Created in 11:11 2018/11/14
     * 根据id获得符合人员积分,根据积分和特殊人群过滤符合人员清单回显 符合人员人数和占比
     * 下面三个版本2.0修改
     */
//    @PostMapping("/getIntegral")
//    public String getIntegral(ActiveScheme activeScheme,HttpServletResponse response){
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        String result = schemeService.getMatterPerson(activeScheme);
//        return result;
//    }


    /**
     * 主动办事项中新建方案,其中针对一个事项新建方案,根据事项id绑定方案完成新建
     *
     * 在主动办方案中,首先匹配事项名称,根据事项名称选择事项(事项名有重复,需传主键id区分匹配)
     *
     * 修改,在新建方案需要根据方案新建保存 最后查询符合人员信息添加到last_person表
     * @param activeScheme
     * @return
     */
//    @PostMapping("/addActiveScheme")
//    public String addActiveScheme(ActiveScheme activeScheme,HttpServletResponse response,HttpServletRequest request){
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        if (activeScheme.getAmId() == null) return "请传入修改id";
//        ActiveMatter matterById = matterService.getMatterById(activeScheme.getAmId());
//        //activeScheme.setAmId(activeScheme.getAmId());//关联id
//        if (matterById.getId() == null) return "没有关联事项";
//        activeScheme.setMatterName(matterById.getMatter()); //事项名称 怕事项名称在保存是修改不完全匹配
//        activeScheme.setCreateTime(new Date());//创建时间
//        String name = CookieUtil.getUid(request, "name"); //去登入用户信息
//        //报批人姓名 需对接1call办获取工作人员姓名和提交审核人员姓名~~
//        if (StringUtils.isEmpty(activeScheme.getWorkName()))activeScheme.setWorkName(name);
//        if (StringUtils.isEmpty(activeScheme.getAgreeName()))activeScheme.setAgreeName("刘佳明");//审批人姓名
//        activeScheme.setSchemeStart(1); //方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
//        if (activeScheme.getIntegral() == null) activeScheme.setIntegral(0); //积分扣减 不传默认0分
//        String result = schemeService.addActiveScheme(activeScheme);
//        return result;
//    }

    /**
     * @Author: x
     * @Date: Created in 14:22 2018/11/15
     * 主动办方案修改接口  如果修改事项名称 需要走事项名称搜索查询道本平台对应事项id(amid必传)!
     */
//    @PostMapping("/updateScheme")
//    public String updateScheme(ActiveScheme activeScheme,HttpServletResponse response){
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        if (activeScheme.getId() == null) return "请传入修改id";
//        int result = schemeService.updateScheme(activeScheme);
//        if (result>0)return "success";
//        return "error";
//    }



    /**
    * @Author: x
    * @Date: Created in 14:52 2018/11/15
     * 分页展示已新建主动办方案 其中分4个状态
     * start : 方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结) ', 2019/2/14 新增状态5: 定时方案
    */
    @PostMapping("/findScheme/getAll") //分页查看事项
    public Map<String, Object> getMatterAll(String pages, String rows, String start, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (StringUtils.isEmpty(start)) {
            Page page = PageHelperUtil.getPage(pages, rows);
            Page list = (Page) schemeService.findAll();
            Map<String, Object> map = new HashMap<>();
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
            return map;
        }else if (start.equals("5")){
            Page page = PageHelperUtil.getPage(pages, rows);
            Page list = (Page) schemeService.findAllQuartz(start);
            Map<String, Object> map = new HashMap<>();
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
            return map;
        } else {
            Page page = PageHelperUtil.getPage(pages, rows);
            Page list = (Page) schemeService.findAllInStart(start);
            Map<String, Object> map = new HashMap<>();
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
            return map;
        }

    }

    /**
    * @Author: x
    * @Date: Created in 14:37 2018/11/15
     * 根据id查看主动办方案
    */
    @GetMapping("/getSchemeById")
    public String getSchemeById(Integer id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (id == null) return "请传入修改id";
        ActiveScheme activeScheme = schemeService.getSchemeById(id);
        return JSON.toJSONString(activeScheme);
    }


    /**
    * @Author: x
    * @Date: Created in 16:13 2018/11/15
     * id :方案id 主键
     * 方案人员清单查看(主动办客服查看和办理) 方案新建成功查看符合人员查看1do推送符合人员,不存在无人员情况
     * 根据方案保存的积分区间和特殊人群信息查询过滤人员信息,分页展示
     * 主动办方案通知状态 1:办理中,2:已办结 start :1或2
     * 只针对已批准方案出现方案通知状态
     * 方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
    */
   @GetMapping("/getSchemePerson")
   public String getSchemePerson(String start,Integer id,String pages ,String rows,HttpServletResponse response){
       response.addHeader("Access-Control-Allow-Origin", "*");
       if (id == null) return "请传入修改id";
       Map<String, Object> schemePerson = schemeService.getSchemePerson(start,id,pages,rows);
       return JSON.toJSONString(schemePerson);
}


    /**
    * @Author: x
    * @Date: Created in 18:47 2018/11/15
     * 删除方案 根据id 主键
    */
    @GetMapping("/deleteScheme")
    public String deleteScheme(Integer id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (id == null) return "请传入修改id";
        schemeService.deleteScheme(id);
        return "success";
    }

    /**
    * @Author: x
    * @Date: Created in 10:21 2018/11/16
     * 调用1do发送方案工单
     * ids 方案id 可单独报批和批量报批
    */
    @PostMapping("/createSchemeOneDo")
    public String createSchemeOneDo(@RequestParam("ids[]") ArrayList<Integer> ids, HttpServletResponse response,HttpServletRequest request){
        response.addHeader("Access-Control-Allow-Origin", "*");
        logger.info("入参:" + ids.toString());
        if (ids.size() >= 0 && ids != null) {
            String name = CookieUtil.getUid(request, "name");
            String showId = CookieUtil.getUid(request, "id");
            logger.info("name:----->>>"+name);
            logger.info("id:----->>>"+showId);
            if (name == null || showId ==null) return "error";
//            String name = "余念";
//            String showId = "XzaqamVZPDtAR3WP";
            int result = schemeService.createSchemeOneDo(ids,name,showId);
            logger.info("查询得到的结果:" + result);
            return result > 0 ? "success" : "error";
        } else {
            return "error";
        }
    }


    /**
     * @Author: x
     * @Date: Created in 14:40 2018/11/16
     * 方案4种状态接口开放方胜群,接受 方案id  方案状态 方案审核通过时间
     * 方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
     */
    @PostMapping("/getSchemeStart")
    public String getSchemeStart(ActiveScheme activeScheme){
        logger.info("id:::::::"+activeScheme.getId());
        logger.info("start:::::::"+activeScheme.getSchemeStart());
        logger.info("DATE:::::::"+activeScheme.getExamineTime());
        if (activeScheme.getId() == null || activeScheme.getSchemeStart()==null
                || activeScheme.getExamineTime()== null) return "error";
        String result = schemeService.setSchemeStart(activeScheme);
        logger.info("方案修改::"+result);
        //方案审核通过后 根据方案id查询方案服务方式 发送通知 加了try
        String result1 =schemeService.combination(activeScheme.getId());
        logger.info("组合方式::"+result1);
        //最新动态中，只要通知了某人，就要显示，不以人员办结状态为依据； 设计新表 记录通知方式和人员
        return result;
    }


    /**
    * @Author: x
    * @Date: Created in 17:13 2019/3/12
     *  统计分析模块展示最新动态效果
    */
    @PostMapping("/combination")
    public String combination(Integer id){
        if (id == null) return "error";
        logger.info("执行方案id"+id);
        int result = statisticsService.insertNotice(id);
        logger.info("通知表数据插入条数"+result);
        return "success";
    }


    //todo 新建方案修改积分接口对接获取 过滤条件变化

    //对接获取积分接口 2.0 修改
    @PostMapping("/getIntegral2")
    public String getIntegral2(ActiveScheme activeScheme,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String result = schemeService.getMatterPerson2(activeScheme);
        return result;
    }

    //对接积分接口 方案新增查询积分项 2.0 修改
    @PostMapping("/addActiveScheme2")
    public String addActiveScheme2(ActiveScheme activeScheme,HttpServletResponse response,HttpServletRequest request){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String name = CookieUtil.getUid(request, "name"); //去登入用户信息
//        String name = "余年";
        if (name == null) return "无用户登入信息";
        String result = schemeService.addActiveScheme1(activeScheme,name);
        return result;
    }

    //对接积分接口 编辑方案查询积分项 2.0 修改
    @PostMapping("/updateScheme2")
    public String updateScheme2(ActiveScheme activeScheme,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (activeScheme.getId() == null) return "请传入修改id";
        int result = schemeService.updateScheme2(activeScheme);
        if (result>0)return "success";
        return "error";
    }
}
