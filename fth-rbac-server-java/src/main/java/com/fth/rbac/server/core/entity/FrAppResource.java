package com.fth.rbac.server.core.entity;

import java.util.Date;

public class FrAppResource {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.app_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Integer appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.resource_name
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private String resourceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.parent_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private String parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.type
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Byte type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.page_url
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private String pageUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.sort
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Byte sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.in_menu
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Byte inMenu;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.creator
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Integer creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fr_app_resource.created_at
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_resource
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public FrAppResource(String id, Integer appId, String resourceName, String parentId, Byte type, String pageUrl, Byte sort, Byte inMenu, Integer creator, Date createdAt) {
        this.id = id;
        this.appId = appId;
        this.resourceName = resourceName;
        this.parentId = parentId;
        this.type = type;
        this.pageUrl = pageUrl;
        this.sort = sort;
        this.inMenu = inMenu;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fr_app_resource
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public FrAppResource() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.id
     *
     * @return the value of fr_app_resource.id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.id
     *
     * @param id the value for fr_app_resource.id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.app_id
     *
     * @return the value of fr_app_resource.app_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.app_id
     *
     * @param appId the value for fr_app_resource.app_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.resource_name
     *
     * @return the value of fr_app_resource.resource_name
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.resource_name
     *
     * @param resourceName the value for fr_app_resource.resource_name
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.parent_id
     *
     * @return the value of fr_app_resource.parent_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.parent_id
     *
     * @param parentId the value for fr_app_resource.parent_id
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.type
     *
     * @return the value of fr_app_resource.type
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.type
     *
     * @param type the value for fr_app_resource.type
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.page_url
     *
     * @return the value of fr_app_resource.page_url
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.page_url
     *
     * @param pageUrl the value for fr_app_resource.page_url
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.sort
     *
     * @return the value of fr_app_resource.sort
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Byte getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.sort
     *
     * @param sort the value for fr_app_resource.sort
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setSort(Byte sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.in_menu
     *
     * @return the value of fr_app_resource.in_menu
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Byte getInMenu() {
        return inMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.in_menu
     *
     * @param inMenu the value for fr_app_resource.in_menu
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setInMenu(Byte inMenu) {
        this.inMenu = inMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.creator
     *
     * @return the value of fr_app_resource.creator
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.creator
     *
     * @param creator the value for fr_app_resource.creator
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fr_app_resource.created_at
     *
     * @return the value of fr_app_resource.created_at
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fr_app_resource.created_at
     *
     * @param createdAt the value for fr_app_resource.created_at
     *
     * @mbg.generated Sat Nov 14 14:01:54 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}