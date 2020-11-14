package com.fth.rbac.server.core.mapper;

import com.fth.rbac.server.core.entity.FrAppApplication;
import com.fth.rbac.server.core.entity.FrAppApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FrAppApplicationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    long countByExample(FrAppApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int deleteByExample(FrAppApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int insert(FrAppApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int insertSelective(FrAppApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    List<FrAppApplication> selectByExample(FrAppApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    FrAppApplication selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") FrAppApplication record, @Param("example") FrAppApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByExample(@Param("record") FrAppApplication record, @Param("example") FrAppApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByPrimaryKeySelective(FrAppApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_application
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByPrimaryKey(FrAppApplication record);
}