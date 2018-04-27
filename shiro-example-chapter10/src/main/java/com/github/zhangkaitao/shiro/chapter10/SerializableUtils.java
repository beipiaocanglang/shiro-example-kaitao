package com.github.zhangkaitao.shiro.chapter10;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具
 */
public class SerializableUtils {

    //序列化
    public static String serialize(Session session) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            String base64Str = Base64.encodeToString(bos.toByteArray());
            return base64Str;
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }
    //反序列化
    public static Session deserialize(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            Session session = (Session)ois.readObject();
            return session;
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }
}
