package com.nateshao.dao;

import com.nateshao.po.BaseDict;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 22:46
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface BaseDictDao {
    // 根据类别代码查询数据字典
    public List<BaseDict> selectBaseDictByTypeCode(String typecode);
}
