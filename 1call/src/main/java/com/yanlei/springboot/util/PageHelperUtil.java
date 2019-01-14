package com.yanlei.springboot.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yanlei.springboot.model.SchemePerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: x
 * @Date: Created in 15:14 2018/11/15
 */
public class PageHelperUtil {

    public static Page getPage(String pages, String rows){
        String pageS = pages == null || pages.trim().length() == 0 ? "1":pages;
        String pageSizes = rows == null || rows.trim().length() == 0 ? "10":rows;
        Integer page = Integer.parseInt(pageS);
        Integer pageSize = Integer.parseInt(pageSizes);
        //设置分页的起始码以及页面大小
        Page Pagehelper = PageHelper.startPage(page, pageSize);
        return Pagehelper;
    }

}
