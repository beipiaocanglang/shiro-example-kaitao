package com.github.zhangkaitao.shiro.chapter5.hash;

import junit.framework.Assert;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.*;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;
import java.security.*;

/**
 * 编码/解码、散列算法 测试Demo
 * author : sunpanhu
 * createTime : 2018/4/23 下午2:58
 */
public class CodecAndCryptoTest {

    /**
     * base64编码/解码操作
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:33
     */
    @Test
    public void testBase64() {
        //初始化一个字符串
        String str = "hello";
        //base64加密
        String base64Encoded = Base64.encodeToString(str.getBytes());
        //Base64解密
        String str2 = Base64.decodeToString(base64Encoded);
        //断言 加密前 和 加密后再解密的结果是否一样
        Assert.assertEquals(str, str2);
    }

    /**
     * 16进制字符串编码/解码操作
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:37
     */
    @Test
    public void testHex() {
        String str = "hello";
        //加密
        String hexEncoded = Hex.encodeToString(str.getBytes());
        //解密
        String str2 = new String(Hex.decode(hexEncoded.getBytes()));
        Assert.assertEquals(str, str2);
    }

    /**
     * byte数组/String之间转换
     * CodecSupport，提供了toBytes(str, "utf-8") / toString(bytes, "utf-8")
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:39
     */
    @Test
    public void testCodecSupport() {
        String str = "hello";
        //字符串转byte[]
        byte[] bytes = CodecSupport.toBytes(str, "utf-8");
        //byte[]转字符串
        String str2 = CodecSupport.toString(bytes, "utf-8");
        Assert.assertEquals(str, str2);
    }


    /**
     * 散列算法
     *      散列算法一般用于生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，常见的散列算法如MD5、SHA等。
     *      一般进行散列时最好提供一个salt（盐），
     *      比如加密密码“admin”，产生的散列值是“21232f297a57a5a743894a0e4a801fc3”，可以到一些md5解密网站很容易的通过散列值得到密码“admin”，
     *      即如果直接对密码进行散列相对来说破解更容易，
     *      此时我们可以加一些只有系统知道的干扰数据，如用户名和ID（即盐）；这样散列的对象是“密码+用户名+ID”，
     *      这样生成的散列值相对来说更难破解
     */
    /**
     * 散列算法 - MD5
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:42
     */
    @Test
    public void testMd5() {
        String str = "hello";
        String salt = "123";
        //通过盐“123”MD5散列“hello”  86fcb4c0551ea48ede7df5ed9626eee7
        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
        //另外散列时还可以指定散列次数，如2次表示：md5(md5(str))。 c942f011ced5f36de066dd2d948538cb
        String md5Salt = new Md5Hash(str, salt, 2).toString();//还可以转换为 toBase64()/toHex()
        System.out.println(md5);
        System.out.println(md5Salt);
    }

    /**
     * 使用SHA256算法生成相应的散列数据，另外还有如SHA1、SHA512算法。
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:48
     */
    @Test
       public void testSha256() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);
    }
    @Test
    public void testSha384() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha384Hash(str, salt).toString();
        System.out.println(sha1);
    }
    @Test
    public void testSha512() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha512Hash(str, salt).toString();
        System.out.println(sha1);
    }

    /**
     * 散列算法 - 通用的散列算法
     * 通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:51
     */
    @Test
    public void testSimpleHash() {
        String str = "hello";
        String salt = "123";
        //MessageDigest 参数1：算法名称、参数2：数据源、参数3：盐
        //SHA-1算法
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();//f0a139408f7b134c66342e3d1cf4870a293c11c3
        System.out.println(simpleHash);
        //MD5算法
        String simpleHash1 = new SimpleHash("MD5", str, salt).toString();//86fcb4c0551ea48ede7df5ed9626eee7
        System.out.println(simpleHash1);
        String md5 = new Md5Hash(str, salt).toString();
        Assert.assertEquals(simpleHash, md5);
    }

    /**
     * 散列算法 - shiro默认提供的散列算法HashService，默认的实现类DefaultHashService
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:26
     */
    @Test
    public void testHashService() {
        //1、首先创建一个DefaultHashService，默认使用SHA-512算法；
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        //2、可以通过hashAlgorithmName属性修改算法；
        hashService.setHashAlgorithmName("SHA-512");
        //3、可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        //4、可以通过generatePublicSalt属性在用户没有传入公盐的情况下是否生成公盐；
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        //5、可以设置randomNumberGenerator用于生成公盐；生成随机数
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        //6、可以设置hashIterations属性来修改默认加密迭代次数；
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        //7、需要构建一个HashRequest，传入算法、数据、公盐、迭代次数。
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();

        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }

    /**
     * 生成随机数
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:31
     */
    @Test
    public void testRandom() {
        //生成随机数
        SecureRandomNumberGenerator randomNum = new SecureRandomNumberGenerator();
        randomNum.setSeed("123".getBytes());
        String randNum = randomNum.nextBytes().toHex();
        System.out.println(randNum);
    }

    /**
     * 对称式加密/解密算法的支持 - AES算法实现
     * author : sunpanhu
     * createTime : 2018/3/28 下午12:32
     */
    @Test
    public void testAesCipherService() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度

        //生成key
        Key key = aesCipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    /**
     * Blowfish
     * author : sunpanhu
     * createTime : 2018/4/23 下午3:19
     */
    @Test
    public void testBlowfishCipherService() {
        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
        blowfishCipherService.setKeySize(128);

        //生成key
        Key key = blowfishCipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = blowfishCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    @Test
    public void testDefaultBlockCipherService() throws Exception {
        //对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
        DefaultBlockCipherService cipherService = new DefaultBlockCipherService("AES");
        cipherService.setKeySize(128);

        //生成key
        Key key = cipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(cipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    //加密/解密相关知识可参考snowolf的博客 http://snowolf.iteye.com/category/68576
}
