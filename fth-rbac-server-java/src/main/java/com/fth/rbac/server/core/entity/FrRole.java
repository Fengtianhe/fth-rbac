package com.fth.rbac.server.core.entity;

import java.util.Date;

public class FrRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_role.id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_role.role_name
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_role.app_id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    private String appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_role.creator
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    private Integer creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_role.created_at
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_role
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public FrRole(String id, String roleName, String appId, Integer creator, Date createdAt) {
        this.id = id;
        this.roleName = roleName;
        this.appId = appId;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_role
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public FrRole() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_role.id
     *
     * @return the value of fr_role.id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_role.id
     *
     * @param id the value for fr_role.id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_role.role_name
     *
     * @return the value of fr_role.role_name
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_role.role_name
     *
     * @param roleName the value for fr_role.role_name
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_role.app_id
     *
     * @return the value of fr_role.app_id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_role.app_id
     *
     * @param appId the value for fr_role.app_id
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_role.creator
     *
     * @return the value of fr_role.creator
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_role.creator
     *
     * @param creator the value for fr_role.creator
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_role.created_at
     *
     * @return the value of fr_role.created_at
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_role.created_at
     *
     * @param createdAt the value for fr_role.created_at
     *
     * @mbg.generated Sun Dec 06 00:22:37 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}