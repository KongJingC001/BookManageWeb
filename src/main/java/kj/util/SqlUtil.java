package kj.util;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class SqlUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("config/mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private SqlUtil() {}

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

}
