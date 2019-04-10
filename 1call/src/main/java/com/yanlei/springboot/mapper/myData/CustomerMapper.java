package com.yanlei.springboot.mapper.myData;

import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 17:23 2018/11/22
 */
@Repository
public interface CustomerMapper {

    List<ActiveScheme> findAllInStart(Map<String, Object> maps);

    @Update("update last_person set customer_start = 2  where id = #{id} ")
    int updatePersonStart(Integer id);

    ActiveScheme getSchemeLog(Integer id);

    List<String> getActiveSchemeByIds(String telephone);

    List<SchemePerson> getMsg(List<String> ids);

    void setMsgStart(SchemePerson schemePerson);

    ActiveScheme getActiveSchemeByPersonId(Integer id);

    @Update("UPDATE active_scheme SET execution_start = #{executionStart} WHERE id = #{id}")
    void overScheme(HashMap<String, Object> map);

    @Select("SELECT id,name,id_number AS idNumber from last_person WHERE id = #{id}")
    SchemePerson findPersonById(Integer id);
}
