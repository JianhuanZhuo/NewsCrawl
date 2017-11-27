package cn.keepfight.Mapper;

import cn.keepfight.newsCrawl.IPProxyDao;

import java.util.List;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public interface IPProxyMapper {

    void insert(IPProxyDao dao) throws Exception;

    void update(IPProxyDao dao) throws Exception;

    List<IPProxyDao> selectAll() throws Exception;

    IPProxyDao selectById(int id) throws Exception;

    void deleteById(int id) throws Exception;
}
