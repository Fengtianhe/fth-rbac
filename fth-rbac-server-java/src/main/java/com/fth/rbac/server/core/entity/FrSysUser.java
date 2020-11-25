package com.fth.rbac.server.core.entity;

import java.util.Date;

public class FrSysUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_sys_user.id
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_sys_user.username
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_sys_user.password
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_sys_user.status
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    private Byte status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_sys_user.created_at
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_sys_user
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public FrSysUser(Integer id, String username, String password, Byte status, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_sys_user
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public FrSysUser() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_sys_user.id
     *
     * @return the value of fr_sys_user.id
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_sys_user.id
     *
     * @param id the value for fr_sys_user.id
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_sys_user.username
     *
     * @return the value of fr_sys_user.username
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_sys_user.username
     *
     * @param username the value for fr_sys_user.username
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_sys_user.password
     *
     * @return the value of fr_sys_user.password
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_sys_user.password
     *
     * @param password the value for fr_sys_user.password
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_sys_user.status
     *
     * @return the value of fr_sys_user.status
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_sys_user.status
     *
     * @param status the value for fr_sys_user.status
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_sys_user.created_at
     *
     * @return the value of fr_sys_user.created_at
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_sys_user.created_at
     *
     * @param createdAt the value for fr_sys_user.created_at
     *
     * @mbg.generated Wed Nov 25 09:53:39 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}