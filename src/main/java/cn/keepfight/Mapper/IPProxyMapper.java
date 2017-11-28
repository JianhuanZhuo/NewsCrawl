package cn.keepfight.Mapper;

import cn.keepfight.dao.IPProxyDao;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public interface IPProxyMapper {

    void insert(IPProxyDao dao) throws Exception;

    void insertList(List<IPProxyDao> daoList) throws Exception;

    List<IPProxyDao> selectPage() throws Exception;

    void deleteById(int id) throws Exception;

    IPProxyDao pickLastTime() throws Exception;

    void updateLastByID(@Param("id") int id,
                        @Param("lasttime") Timestamp lasttime) throws Exception;

    void goodByID(@Param("id") int id) throws Exception;

    void badByID(@Param("id") int id) throws Exception;
}
