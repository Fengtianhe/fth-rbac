package com.fth.rbac.server.core.entity;

import java.util.Date;

public class FrResource {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.app_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private String appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.resource_name
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private String resourceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.parent_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private String parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.type
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.page_url
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private String pageUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.sort
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.in_menu
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Integer inMenu;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.status
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.creator
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Integer creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_resource.created_at
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_resource
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public FrResource(String id, String appId, String resourceName, String parentId, Integer type, String pageUrl, Integer sort, Integer inMenu, Integer status, Integer creator, Date createdAt) {
        this.id = id;
        this.appId = appId;
        this.resourceName = resourceName;
        this.parentId = parentId;
        this.type = type;
        this.pageUrl = pageUrl;
        this.sort = sort;
        this.inMenu = inMenu;
        this.status = status;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_resource
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public FrResource() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.id
     *
     * @return the value of fr_resource.id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.id
     *
     * @param id the value for fr_resource.id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.app_id
     *
     * @return the value of fr_resource.app_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.app_id
     *
     * @param appId the value for fr_resource.app_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.resource_name
     *
     * @return the value of fr_resource.resource_name
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.resource_name
     *
     * @param resourceName the value for fr_resource.resource_name
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.parent_id
     *
     * @return the value of fr_resource.parent_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.parent_id
     *
     * @param parentId the value for fr_resource.parent_id
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.type
     *
     * @return the value of fr_resource.type
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.type
     *
     * @param type the value for fr_resource.type
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.page_url
     *
     * @return the value of fr_resource.page_url
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.page_url
     *
     * @param pageUrl the value for fr_resource.page_url
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.sort
     *
     * @return the value of fr_resource.sort
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.sort
     *
     * @param sort the value for fr_resource.sort
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.in_menu
     *
     * @return the value of fr_resource.in_menu
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Integer getInMenu() {
        return inMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.in_menu
     *
     * @param inMenu the value for fr_resource.in_menu
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setInMenu(Integer inMenu) {
        this.inMenu = inMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.status
     *
     * @return the value of fr_resource.status
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.status
     *
     * @param status the value for fr_resource.status
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.creator
     *
     * @return the value of fr_resource.creator
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.creator
     *
     * @param creator the value for fr_resource.creator
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_resource.created_at
     *
     * @return the value of fr_resource.created_at
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_resource.created_at
     *
     * @param createdAt the value for fr_resource.created_at
     *
     * @mbg.generated Thu Dec 03 18:29:49 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}