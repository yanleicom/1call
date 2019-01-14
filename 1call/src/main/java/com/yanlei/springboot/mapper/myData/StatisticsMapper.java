package com.yanlei.springboot.mapper.myData;

import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 16:43 2019/1/8
 */
@Repository
public interface StatisticsMapper {
    @Select("SELECT COUNT(la.id) FROM (SELECT id from active_scheme WHERE " +
            "scheme_start = #{over} OR scheme_start = #{examine} ) sc LEFT JOIN last_person la ON sc.id=la.s_id")
    Integer getSchemeOverById(Map<String, String> value);

    @Select("SELECT id , waiter_scheme as waiterScheme from active_scheme WHERE scheme_start = #{value}")
    List<ActiveScheme> getSchemeThreeById(String value);

    @Select("SELECT COUNT(id) FROM active_scheme")
    Integer getSchemeCount();

    @Select("SELECT COUNT(sc.p_id) FROM active_matter ac LEFT JOIN scheme_person sc ON ac.id=sc.p_id")
    Integer getMatterPerson();

    @Select("SELECT COUNT(id) FROM active_matter")
    Integer getMatterCount();

    @Select("SELECT id,matter_name AS matterName,waiter_scheme AS waiterScheme,end_time AS endTime FROM " +
            "active_scheme WHERE scheme_start =#{value} ORDER BY end_time DESC LIMIT 1")
    ActiveScheme getSchemeOver(String value);

    @Select("SELECT sc.y AS matterName,sc.m AS schemeName,COUNT(sc.m) AS integral FROM \n" +
            "(SELECT id,DATE_FORMAT(create_time,'%Y') y,DATE_FORMAT(create_time,'%m') m \n" +
            "FROM active_scheme ) sc LEFT JOIN last_person la ON sc.id = la.s_id GROUP BY sc.y,sc.m")
    List<ActiveScheme> getScheme();

    @Select("SELECT COUNT(id) AS peopleNumber,scheme_start AS schemeStart FROM active_scheme GROUP BY scheme_start")
    List<ActiveScheme> getSchemeStart();

    @Select("SELECT ac.matter,ac.id FROM active_matter ac RIGHT JOIN\n" +
            "scheme_person sc ON ac.id=sc.p_id WHERE ac.matter IS NOT NULL GROUP BY sc.p_id LIMIT 10 ")
    List<ActiveMatter> getMatter();

    @Select("SELECT `name`,id_number AS idNumber,telephone FROM scheme_person WHERE p_id = #{id}")
    List<SchemePerson> getMatterPersonById(Integer id);

    @Select("SELECT COUNT(la.id) count FROM (SELECT id FROM active_scheme \n" +
            "WHERE matter_name = #{matter1}) ac LEFT JOIN \n" +
            "last_person la ON ac.id=la.s_id GROUP BY la.id_number")
    List<String> getSchemeByMatterName(String matter1);

    @Select("SELECT la.`name`,la.id_number AS idNumber,la.telephone FROM (SELECT id FROM active_scheme \n" +
            "WHERE matter_name = #{matter1}) ac LEFT JOIN \n" +
            "last_person la on ac.id=la.s_id WHERE la.`name` IS NOT NULL")
    List<SchemePerson> getMatterByName(String matter1);
}
