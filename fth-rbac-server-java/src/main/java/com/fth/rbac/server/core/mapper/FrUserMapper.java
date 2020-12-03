package com.fth.rbac.server.core.mapper;

import com.fth.rbac.server.core.entity.FrUser;
import com.fth.rbac.server.core.entity.FrUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FrUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    long countByExample(FrUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int deleteByExample(FrUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int insert(FrUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int insertSelective(FrUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    List<FrUser> selectByExample(FrUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    FrUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int updateByExampleSelective(@Param("record") FrUser record, @Param("example") FrUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int updateByExample(@Param("record") FrUser record, @Param("example") FrUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int updateByPrimaryKeySelective(FrUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_user
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    int updateByPrimaryKey(FrUser record);
}