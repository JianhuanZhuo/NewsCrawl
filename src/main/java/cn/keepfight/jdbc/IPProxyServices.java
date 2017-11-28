package cn.keepfight.jdbc;

import cn.keepfight.Mapper.IPProxyMapper;
import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.utils.FXUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Timestamp;
import java.util.List;

public abstract class IPProxyServices {
    private static SqlSessionFactory factory = SqlSessionServices.getFactory();

    public static void insert(IPProxyDao dao) throws Exception {
        FXUtils.getMapper(factory, IPProxyMapper.class, IPProxyMapper::insert, dao);
    }

    @Deprecated
    public static void insertList(List<IPProxyDao> daoList) throws Exception {
        FXUtils.getMapper(factory, IPProxyMapper.class, IPProxyMapper::insertList, daoList);
    }

    public static List<IPProxyDao> selectPage() throws Exception {
        return FXUtils.getMapper(factory, IPProxyMapper.class, IPProxyMapper::selectPage);
    }

    public static void deleteById(int id) throws Exception {
        FXUtils.getMapper(factory, IPProxyMapper.class, IPProxyMapper::deleteById, id);
    }


    public static synchronized IPProxyDao pickLastTime() throws Exception {
        IPProxyDao res = FXUtils.getMapper(factory, IPProxyMapper.class, IPProxyMapper::pickLastTime);
        if (res != null) updateLastByID(res.getId(), new Timestamp(System.currentTimeMillis()));
        return res;
    }

    public static void updateLastByID(int id, Timestamp lasttime) throws Exception {
        try (SqlSession session = factory.openSession(true)) {
            session.getMapper(IPProxyMapper.class).updateLastByID(id, lasttime);
        }
    }

    public static void goodByID(int id) throws Exception {
        try (SqlSession session = factory.openSession(true)) {
            session.getMapper(IPProxyMapper.class).goodByID(id);
        }
    }

    public static void badByID(int id) throws Exception {
        try (SqlSession session = factory.openSession(true)) {
            session.getMapper(IPProxyMapper.class).badByID(id);
        }
    }
}
