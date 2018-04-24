package com.github.zhangkaitao.shiro.chapter5.hash;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Test;

/**
 * DefaultPasswordService配合PasswordMatcher实现简单的密码加密与验证服务
 * author : sunpanhu
 * createTime : 2018/4/23 下午2:59
 */
public class PasswordTest extends BaseTest {

    /**
     * 本地测试
     * author : sunpanhu
     * createTime : 2018/4/2 上午10:31
     */
    @Test
    public void testPasswordServiceWithMyRealm() {

        login("classpath:shiro-passwordservice.ini", "wu", "123");
    }

    /**
     * 使用数据库数据
     * author : sunpanhu
     * createTime : 2018/4/2 上午10:31
     */
    @Test
    public void testPasswordServiceWithJdbcRealm() {

        login("classpath:shiro-jdbc-passwordservice.ini", "zhang", "123");
    }

    /**
     * 生成密码散列值
     * HashedCredentialsMatcher实现密码验证服务
     * 它只用于密码验证，且可以提供自己的盐，而不是随机生成盐，且生成密码散列值的算法需要自己写，因为能提供自己的盐。
     * author : sunpanhu
     * createTime : 2018/4/2 上午10:34
     */
    @Test
    public void testGeneratePassword() {
        //算法名称
        String algorithmName = "md5";
        //用户名
        String username = "liu";
        //密码
        String password = "123";

        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println(salt2);

        int hashIterations = 2;
        //参数1：算法名称、参数2：密码、参数3：盐、参数4：加密次数
        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);

        ///获取散列的值
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
    }

    /**
     * 和上面的 testGeneratePassword  方法 生成的散列值 进行对比
     * 和上面的方法无关 只是把上面的结果 拿出来用而已
     * author : sunpanhu
     * createTime : 2018/4/2 上午11:03
     */
    @Test
    public void testHashedCredentialsMatcherWithMyRealm2() {
        //使用testGeneratePassword生成的散列密码
        login("classpath:shiro-hashedCredentialsMatcher.ini", "liu", "123");
    }

    /**
     * 使用数据库  和上面的 testGeneratePassword  方法 生成的散列值 进行对比
     * 和上面的方法无关 只是把上面的结果 拿出来用而已
     * author : sunpanhu
     * createTime : 2018/4/2 上午11:03
     */
    @Test
    public void testHashedCredentialsMatcherWithJdbcRealm() {

        //Shiro默认使用了apache commons BeanUtils，默认是不进行Enum类型转型的，此时需要自己注册一个Enum转换器
        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);

        //使用testGeneratePassword生成的散列密码
        login("classpath:shiro-jdbc-hashedCredentialsMatcher.ini", "liu", "123");
    }

    /**
     * Enum转换器
     * author : sunpanhu
     * createTime : 2018/4/2 上午11:03
     */
    private class EnumConverter extends AbstractConverter {
        @Override
        protected String convertToString(final Object value) throws Throwable {
            String name = ((Enum) value).name();
            return name;
        }
        @Override
        protected Object convertToType(final Class type, final Object value) throws Throwable {
            return Enum.valueOf(type, value.toString());
        }

        @Override
        protected Class getDefaultType() {
            return null;
        }
    }

    /**
     * 如果密码输入正确清除cache中的记录；否则cache中的重试次数+1，如果超出5次那么抛出异常表示超出重试次数了。
     * author : sunpanhu
     * createTime : 2018/4/2 上午11:11
     */
    @Test(expected = ExcessiveAttemptsException.class)
    public void testRetryLimitHashedCredentialsMatcherWithMyRealm() {
        for(int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
            } catch (Exception e) {
                /*ignore*/
            }
        }
        login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
    }
}
