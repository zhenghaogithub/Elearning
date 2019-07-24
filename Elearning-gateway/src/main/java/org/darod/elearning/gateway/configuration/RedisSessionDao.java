package org.darod.elearning.gateway.configuration;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.darod.elearning.gateway.utils.RedisUtils;
import org.darod.elearning.gateway.utils.RedisUtilsForShiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/3 0003 20:16
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {
    @Autowired
    RedisUtilsForShiro redisUtils;

    private final String SHIRO_SESSION_PREFIX = "elearning-gateway-session:";

    private String getKey(String key) {
        return SHIRO_SESSION_PREFIX + key;
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            String key = getKey(session.getId().toString());
//            byte[] value = SerializationUtils.serialize(session);
            redisUtils.set(key, session, 86400);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
//        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        String key = getKey(sessionId.toString());
//        return (Session)SerializationUtils.deserialize((byte[]) redisUtils.get(key));
        return (Session) redisUtils.get(key);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null && session.getId() == null) return;
        String key = getKey(session.getId().toString());
        redisUtils.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> keys = redisUtils.keys(SHIRO_SESSION_PREFIX + "*");
        return keys.stream().map(key -> (Session) redisUtils.get(key)).collect(Collectors.toCollection(HashSet::new));
    }
}
