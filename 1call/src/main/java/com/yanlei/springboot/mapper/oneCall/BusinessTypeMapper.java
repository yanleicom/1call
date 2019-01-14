package com.yanlei.springboot.mapper.oneCall;

import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.BusinessType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: x
 * @Date: Created in 16:52 2018/11/7
 */

@Repository
public interface BusinessTypeMapper {

    List<BusinessType> findNameFromBusinessType(ActiveMatter activeMatter);
}
