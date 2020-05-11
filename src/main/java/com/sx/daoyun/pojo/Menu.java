package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private int MenuID;
    private String MenuName;
    private String Icon;
    private String url;
    private int ParentMenuID;
    private String isPage;
    private String Order;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public int getMenuID() {
        return MenuID;
    }

    public void setMenuID(int menuID) {
        MenuID = menuID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParentMenuID() {
        return ParentMenuID;
    }

    public void setParentMenuID(int parentMenuID) {
        ParentMenuID = parentMenuID;
    }

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public String getCreateby() {
        return Createby;
    }

    public void setCreateby(String createby) {
        Createby = createby;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getModifyby() {
        return Modifyby;
    }

    public void setModifyby(String modifyby) {
        Modifyby = modifyby;
    }

    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }
}
