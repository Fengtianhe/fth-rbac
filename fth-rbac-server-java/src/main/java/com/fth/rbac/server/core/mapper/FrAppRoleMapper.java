package com.fth.rbac.server.core.mapper;

import com.fth.rbac.server.core.entity.FrAppRole;
import com.fth.rbac.server.core.entity.FrAppRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FrAppRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    long countByExample(FrAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int deleteByExample(FrAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int insert(FrAppRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int insertSelective(FrAppRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    List<FrAppRole> selectByExample(FrAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") FrAppRole record, @Param("example") FrAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_role
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    int updateByExample(@Param("record") FrAppRole record, @Param("example") FrAppRoleExample example);
}