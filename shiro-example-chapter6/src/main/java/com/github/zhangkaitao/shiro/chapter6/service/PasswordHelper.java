package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 生成散列值 盐值
 */
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    //算法名称
    private String algorithmName = "md5";
    //加盐次数
    private final int hashIterations = 2;

    public void encryptPassword(User user) {

        //获取随机数 设置盐
        String salt = randomNumberGenerator.nextBytes().toHex();
        user.setSalt(salt);

        //使用MD5迭代两次 再转成16进制
        //user.getCredentialsSalt() 用户名加盐
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        //设置密码
        user.setPassword(newPassword);
    }
}
