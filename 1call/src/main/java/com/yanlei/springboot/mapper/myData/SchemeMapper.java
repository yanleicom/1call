package com.yanlei.springboot.mapper.myData;

import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.Integral;
import com.yanlei.springboot.model.SchemePerson;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 17:41 2018/11/13
 */
@Repository
public interface SchemeMapper {
    void addActiveScheme(ActiveScheme activeScheme);

    List<ActiveMatter> findMatterName(ActiveMatter activeMatter);

    Integral getMatterPerson(ActiveScheme activeScheme);

    @Select("select count(0) from scheme_person where p_id = #{amId}")
    Integer getCount(Integer amId);

    @Select("select MIN(integral) from scheme_person where p_id = #{amId}")
    int getMin(Integer amId);

    ActiveScheme getSchemeById(Integer id);

    @Select("SELECT id,scheme_name AS schemeName,create_time AS createTime,work_name AS workName, " +
            "agree_name AS agreeName from active_scheme ORDER BY id DESC")
    List<ActiveScheme> findAll();

    @Select("SELECT id,scheme_name AS schemeName,create_time AS createTime,work_name AS workName, " +
            "agree_name AS agreeName from active_scheme where scheme_start = #{start} ORDER BY id DESC")
    List<ActiveScheme> findAllInStart(String start);

    @Select("SELECT am_id AS amId, special_groups AS specialGroups," +
            "integral_qj AS integralQj from active_scheme where id = #{id}")
    ActiveScheme getSchemePerson(Integer id);

    List<SchemePerson> findSchemePerson(ActiveScheme activeScheme);

    @Delete("delete from active_scheme where id = #{id}")
    void deleteScheme(Integer id);

    int updateScheme(ActiveScheme activeScheme);

    int setSchemeStart(ActiveScheme activeScheme);

    int insertLastPerson(List<SchemePerson> activeScheme);

    @Select(" SELECT id,s_id AS sId,name,id_number AS idNumber,address,special_groups AS specialGroups," +
            "waiter_name AS waiterName,telephone,username,integral,app_start AS appStart, " +
            "msg_start AS msgStart,telephone_start AS telephoneStart, customer_start AS " +
            "customerStart FROM last_person where s_id = #{id}")
    List<SchemePerson> getLastPerson(Integer id);

    @Delete("delete from last_person where s_id = #{id} ")
    void deleteLastPerson(Integer id);

    List<ActiveScheme> findWeekJob(ActiveScheme activeScheme);

//    List<SchemePerson> findSchemePersonNotInNumber(ActiveScheme scheme);

//    List<ActiveScheme> likeSchemeName(String name);

    SchemePerson getLastPersonMaxTime(ActiveScheme scheme);

    List<SchemePerson> findAddSchemePerson(ActiveScheme scheme);

    void addPeopleNumber(ActiveScheme activeScheme);

//    List<SchemePerson> getLastPersonForStart(Map<String,Object> maps);

    List<SchemePerson> getPersonToAppAndMsg(Map<String,Object> maps);

    List<SchemePerson> getAppAndMsgAndTelephone(Map<String,Object> maps);

    List<SchemePerson> getAppAndMsgOrAndCustomer(Map<String,Object> maps);

    List<SchemePerson> getPersonToStartAll(Map<String,Object> maps);

    int setLastPersonMsgStart(HashMap<String, Object> telMap);

    void setSchemeStartOver(HashMap<String, Object> overStart);

    int setLastPersonMsgAndTelStart(HashMap<String, Object> telMap);

    List<String> getSchemeSpecialGroupsPerson(ActiveScheme activeScheme);

    List<SchemePerson> getSchemePersonByIdNumber(ActiveScheme activeScheme1);

    List<String> findAddSchemePerson2(ActiveScheme scheme);

    int setLastPersonMsgStart2(HashMap<String, Object> telMap);

    List<SchemePerson> getLastPerson2(HashMap<String, Object> telMap);

    int setLastPersonMsgAndTelStart2(HashMap<String, Object> callMap);

    List<String> findSchemePerson2(ActiveScheme scheme);

    @Select("SELECT id,scheme_name AS schemeName,create_time AS createTime,work_name AS workName," +
            "agree_name AS agreeName,execution_time AS executionTime,execution_date AS executionDate,execution_start AS executionStart from active_scheme where execution_date  IS NOT NULL AND execution_date <> ''" +
            "AND execution_start IS NOT NULL AND execution_start <> '' AND execution_time IS NOT NULL AND execution_time <> '' ORDER BY id DESC")
    List<ActiveScheme> findAllQuartz(String start);

    //年后修改组合状态
    int setLastPersonTelStart(HashMap<String, Object> overStart);

    List<SchemePerson> getPersonToTelephoneAndCustomer(Map<String, Object> maps);

    List<SchemePerson> getPersonToTelephone(Map<String, Object> maps);

    List<SchemePerson> getPersonToCustomer(Map<String, Object> maps);

    int setLastPersonTelStart2(HashMap<String, Object> telMap);

    @Select("SELECT COUNT(0) FROM active_scheme WHERE as_id = #{id} ")
    int getQuartzCount(Integer id);

    @Update("UPDATE active_scheme SET people_number = #{peopleNumber} WHERE id = #{id}")
    void setSchemePersonNumber(Map<String, Integer> map);
}
