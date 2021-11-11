package com.nateshao.utils;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 22:43
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class Page<T> {
    private int total;    // 总条数
    private int page;     // 当前页
    private int size;     // 每页数
    private List<T> rows; // 结果集
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", page=" + page +
                ", size=" + size +
                ", rows=" + rows +
                '}';
    }
}
