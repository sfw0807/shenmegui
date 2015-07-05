package com.dc.esb.servicegov.util;

import java.io.Serializable;
import java.util.List;


public class TreeNode implements Serializable {
    String id;
    String text;
    String iconCls;
    String checked;
    String state;
    String attributes;
    String target;
    String parentId;
    List<TreeNode> children;
    String append1;
    String append2;
    String append3;
    String append4;
    String append5;
    String click;

    public TreeNode() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getAppend1() {
        return append1;
    }

    public void setAppend1(String append1) {
        this.append1 = append1;
    }

    public String getAppend2() {
        return append2;
    }

    public void setAppend2(String append2) {
        this.append2 = append2;
    }

    public String getAppend3() {
        return append3;
    }

    public void setAppend3(String append3) {
        this.append3 = append3;
    }

    public String getAppend4() {
        return append4;
    }

    public void setAppend4(String append4) {
        this.append4 = append4;
    }

    public String getAppend5() {
        return append5;
    }

    public void setAppend5(String append5) {
        this.append5 = append5;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

}
