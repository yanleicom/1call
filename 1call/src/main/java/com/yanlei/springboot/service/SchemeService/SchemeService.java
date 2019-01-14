package com.yanlei.springboot.service.SchemeService;

import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;

import java.util.List;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 17:29 2018/11/13
 */
public interface SchemeService {
    String addActiveScheme(ActiveScheme activeScheme);

    List<ActiveMatter> findMatterName(ActiveMatter activeMatter);

    String getMatterPerson(ActiveScheme activeScheme);

    ActiveScheme getSchemeById(Integer id);

    List<ActiveScheme> findAll();

    List<ActiveScheme> findAllInStart(String start);

    Map<String, Object> getSchemePerson(String start, Integer id, String pages, String rows);

    void deleteScheme(Integer id);

    int updateScheme(ActiveScheme activeScheme);

    int createSchemeOneDo(List<Integer> ids, String name, String showId);

    String setSchemeStart(ActiveScheme activeScheme);

    int myWeekJobAll();

    int myMonthJobAll();

    String myWeekJobAdd();

    String myMonthJobAdd();

    String addActiveScheme1(ActiveScheme activeScheme, String name);

    String getMatterPerson2(ActiveScheme activeScheme);

    int updateScheme2(ActiveScheme activeScheme);

}
