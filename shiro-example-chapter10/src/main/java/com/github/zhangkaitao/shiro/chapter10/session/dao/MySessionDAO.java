package com.github.zhangkaitao.shiro.chapter10.session.dao;

import com.github.zhangkaitao.shiro.chapter10.JdbcTemplateUtils;
import com.github.zhangkaitao.shiro.chapter10.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 会话存储/持久化
 * Shiro提供SessionDAO用于会话的CRUD，即DAO（Data Access Object）模式实现
 * AbstractSessionDAO提供了SessionDAO的基础实现，如生成会话ID等；
 * CachingSessionDAO提供了对开发者透明的会话缓存的功能，只需要设置相应的CacheManager即可；
 * MemorySessionDAO直接在内存中进行会话维护；
 * EnterpriseCacheSessionDAO提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
 *
 * 在shiro-web.ini中配置 sessionDAO=com.github.zhangkaitao.shiro.chapter10.session.dao.MySessionDAO
 * 其他设置和之前一样，因为继承了CachingSessionDAO；所有在读取时会先查缓存中是否存在，如果找不到才到数据库中查找
 *
 * author : sunpanhu
 * createTime : 2018/4/4 上午11:28
 */
public class MySessionDAO extends CachingSessionDAO {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

    /**
     * 创建
     * 如DefaultSessionManager在创建完session后会调用该方法；
     * 如保存到关系数据库/文件系统/NoSQL数据库；
     * 即可以实现会话的持久化；
     * 返回会话ID；
     * 主要此处返回的ID.equals(session.getId())；
     * author : sunpanhu
     * createTime : 2018/4/4 上午11:28
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String sql = "insert into sessions(id, session) values(?,?)";
        jdbcTemplate.update(sql, sessionId, SerializableUtils.serialize(session));
        return session.getId();
    }
    /**
     * 更新会话；
     * 如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     * author : sunpanhu
     * createTime : 2018/4/4 上午11:28
     */
    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        String sql = "update sessions set session=? where id=?";
        jdbcTemplate.update(sql, SerializableUtils.serialize(session), session.getId());
    }
    /**
     * 删除会话；
     * 当会话过期/会话停止（如用户退出时）会调用
     * author : sunpanhu
     * createTime : 2018/4/4 上午11:28
     */
    @Override
    protected void doDelete(Session session) {
        String sql = "delete from sessions where id=?";
        jdbcTemplate.update(sql, session.getId());
    }
    /**
     * 根据会话ID获取会话
     * author : sunpanhu
     * createTime : 2018/4/4 上午11:28
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String sql = "select session from sessions where id=?";
        List<String> sessionStrList = jdbcTemplate.queryForList(sql, String.class, sessionId);
        if(sessionStrList.size() == 0) return null;
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
}
