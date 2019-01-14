package com.yanlei.springboot.service.MatterService;

import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.BusinessType;

import java.util.List;

/**
 * @Author: x
 * @Date: Created in 15:26 2018/10/29
 */

public interface MatterService {
    String insertMatter(ActiveMatter activeMatter,String name,String showId);

    int deleteMatter(int id);

    List<ActiveMatter> findAll();

    ActiveMatter getMatterById(int id);

    List<BusinessType> findNameFromBusinessType(ActiveMatter activeMatter);

    void updateMatter(ActiveMatter activeMatter);

    List<SchemePerson> showMatterPerson(Integer id);

    String createMatterOneDo(Integer id, String name, String showId);
}
