package com.study.service;

import com.github.pagehelper.PageInfo;
import com.study.model.Resources;
import com.study.util.PageBean;

import java.util.List;
import java.util.Map;

public interface ResourcesService extends IService<Resources> {
    PageInfo<Resources> selectByPage(Resources resources, int start, int length);

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
    public List<Resources> queryByType(Resources resources,PageBean pageBean);
}
