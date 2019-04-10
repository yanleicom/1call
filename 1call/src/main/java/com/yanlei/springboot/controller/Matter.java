package com.yanlei.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yanlei.springboot.mapper.myData.MatterMapper;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.BusinessType;
import com.yanlei.springboot.service.MatterService.MatterService;
import com.yanlei.springboot.util.CookieUtil;
import com.yanlei.springboot.util.PageHelperUtil;
import com.yanlei.springboot.util.Start;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: x
 * @Date: Created in 15:07 2018/10/29
 */

@RestController
public class Matter {

    private Logger logger = LoggerFactory.getLogger(Matter.class);

    @Autowired
    MatterService matterService;
    @Autowired
    MatterMapper matterMapper;
    /**
    * @Author: x
    * @Date: Created in 15:00 2018/10/30
     * 新建事项 首先输入新建事项名称,异步调用接口查询输入%事项名称% 匹配到返回事项名称采取下拉方式(模仿百度搜索)
     * 选取相对于的事项名称回显该事项的事项详情(事项条件)
     *
    */

    //先获得事项名称在保存 查询事项名和事项详情回显
    @PostMapping("/getMatterName")
    public String getMatterName(ActiveMatter activeMatter,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String matter = activeMatter.getMatter(); //事项名称
        String street = activeMatter.getStreet(); //街道 默认武林
        if (StringUtils.isEmpty(street)) activeMatter.setStreet("武林");
        if (StringUtils.isEmpty(matter)){
            return "参数matter为空,请指定事项名称!!!";
        }else {
            List<BusinessType> list = matterService.findNameFromBusinessType(activeMatter);
//            logger.info(JSON.toJSONString(list));
            if (list!=null && list.size()>0){
                return JSON.toJSONString(list);
            }return "sorry,您查找的事项名称未匹配到,请您手动新建~";
        }

    }


    @PostMapping("/addMatter") //新建事项
    public String insertForm(ActiveMatter activeMatter, HttpServletResponse response,HttpServletRequest request) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String name = CookieUtil.getUid(request, "name");
        String showId = CookieUtil.getUid(request, "id");
        logger.info("name:----->>>"+name);
        logger.info("id:----->>>"+showId);
        if (name == null || showId ==null) return "error";
        String result = matterService.insertMatter(activeMatter,name,showId);
        return result;
    }

    /**
    * @Author: x
    * @Date: Created in 17:58 2019/1/9
     * 修改分离新建事项是发送工单问题 修改编辑事项发生工单问题
    */
    @PostMapping("/createMatterOneDo")
    public String createMatterOneDo(Integer id,HttpServletRequest request,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        String name = CookieUtil.getUid(request, "name");
        String showId = CookieUtil.getUid(request, "id");
        logger.info("name:----->>>"+name); //传 name id
        logger.info("id:----->>>"+showId);
//        String name = "余念";
//        String showId = "XzaqamVZPDtAR3WP";
        if (name == null || showId ==null) return "error";
        String result = matterService.createMatterOneDo(id,name,showId);
        return result;
    }

    @GetMapping("/deleteMatter") //事项删除
    public String deleteMatter(int id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        int i =  matterService.deleteMatter(id);
        if (i>0){
           return "success";
        }
        return "error";
    }


    @GetMapping("/findMatter/getAll") //分页查看事项
    public Map<String, Object> getMatterAll(String pages, String rows, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = PageHelperUtil.getPage(pages, rows);
        Page list = (Page) matterService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("total",list.getTotal());
        map.put("rows",list.getResult());
        return map;
    }


    @GetMapping("/getMatterById") //id查询接口
    public String getMatterById(int id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        ActiveMatter activeMatter = matterService.getMatterById(id);
        return JSON.toJSONString(activeMatter);
    }


    @PostMapping("/updateMatter")//编辑事项
    public String updateMatter(ActiveMatter activeMatter,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (activeMatter.getId() == null) return "请传入修改id";
            if (StringUtils.isEmpty(activeMatter.getStreet())) activeMatter.setStreet("武林");
        ActiveMatter matterById = matterService.getMatterById(activeMatter.getId());
        /**
        * @Author: x
        * @Date: Created in 14:31 2019/1/10
         * 2.0 修改
        */
        if (matterById.getMatterStart().equals(Start.contentStart.CODE_YES.getValue())){
            return "已发送1do工单不可修改事项";
        }
        String streetManagerId = activeMatter.getStreetManagerId();
        if (StringUtils.isEmpty(streetManagerId)) return "请添加“街道负责人”!!!";
        String streetManagerName = activeMatter.getStreetManagerName();
        if (StringUtils.isEmpty(streetManagerName)) return "请添加“街道负责人”!!!";
        matterService.updateMatter(activeMatter);
            return "success";
    }


    @PostMapping("/showMatterPerson") //分页查看1do符合人员(模拟数据)
    public String showMatterPerson(String pages, String rows,Integer id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (id == null) return "请传入修改id";
        Page page = PageHelperUtil.getPage(pages, rows);
        Page list = (Page)matterService.showMatterPerson(id);
        if (list.getTotal()>0 && list!=null) {
            Map<String, Object> map = new HashMap<>();
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
            return JSON.toJSONString(map);
        }return "暂无数据";
    }
}
