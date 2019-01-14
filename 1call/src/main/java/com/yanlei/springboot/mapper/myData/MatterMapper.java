package com.yanlei.springboot.mapper.myData;

import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.model.ActiveMatter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 15:53 2018/10/29
 */

@Repository
public interface MatterMapper {

//    @Options(useGeneratedKeys = true ,keyProperty = "id" )
//    @Insert("insert into active_matter (matter,matter_details,details,dissatisfaction,threshold) values" +
//            "(#{matter},#{matterDetails},#{details},#{dissatisfaction},#{threshold})")
    void insertMatter(ActiveMatter activeMatter);

    @Delete("DELETE FROM active_matter where id = #{id}")
    int deleteMatter(int id);

//    @Select("select id,matter,threshold from active_matter ORDER BY id DESC")
    @Select("select a.id,a.matter,a.threshold,a.matter_start AS matterStart,b.num from active_matter a LEFT JOIN " +
            "(SELECT p_id,COUNT(p_id) AS num FROM scheme_person GROUP BY p_id)b ON " +
            "a.id = b.p_id ORDER BY a.id DESC")
    List<ActiveMatter> findAll();

    @Select("select id,matter,matter_details AS matterDetails,details," +
            "dissatisfaction,threshold,street from active_matter where id = #{id}")
    ActiveMatter getMatterById(int id);

    void updateMatter(ActiveMatter activeMatter);

    List<SchemePerson> showMatterPerson(Integer id);

    @Update("UPDATE active_matter SET matter_start = #{start} WHERE id = #{id} ")
    void updateMatterStart(Map<String, Object> value);
}
