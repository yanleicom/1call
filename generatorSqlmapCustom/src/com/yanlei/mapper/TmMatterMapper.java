package com.yanlei.mapper;

import com.yanlei.pojo.TmMatter;
import com.yanlei.pojo.TmMatterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TmMatterMapper {
    int countByExample(TmMatterExample example);

    int deleteByExample(TmMatterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TmMatter record);

    int insertSelective(TmMatter record);

    List<TmMatter> selectByExample(TmMatterExample example);

    TmMatter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TmMatter record, @Param("example") TmMatterExample example);

    int updateByExample(@Param("record") TmMatter record, @Param("example") TmMatterExample example);

    int updateByPrimaryKeySelective(TmMatter record);

    int updateByPrimaryKey(TmMatter record);
}