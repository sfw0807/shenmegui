package com.dc.esb.servicegov.vo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-2
 * Time: 下午5:04
 */
public class TreeNode {
    private String title;
    private String isFolder;
    private List<TreeNode> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFolder() {
        return isFolder;
    }

    public void setFolder(String folder) {
        isFolder = folder;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
