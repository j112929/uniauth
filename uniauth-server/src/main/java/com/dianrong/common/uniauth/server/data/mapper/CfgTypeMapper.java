package com.dianrong.common.uniauth.server.data.mapper;

import com.dianrong.common.uniauth.server.data.entity.CfgType;
import com.dianrong.common.uniauth.server.data.entity.CfgTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CfgTypeMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int countByExample(CfgTypeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int deleteByExample(CfgTypeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int deleteByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int insert(CfgType record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int insertSelective(CfgType record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  List<CfgType> selectByExample(CfgTypeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  CfgType selectByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int updateByExampleSelective(@Param("record") CfgType record,
      @Param("example") CfgTypeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int updateByExample(@Param("record") CfgType record, @Param("example") CfgTypeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int updateByPrimaryKeySelective(CfgType record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * cfg_type
   *
   * @mbggenerated Tue Mar 29 15:10:36 CST 2016
   */
  int updateByPrimaryKey(CfgType record);
}