package com.github.zhangkaitao.shiro.chapter22.jcaptcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

import javax.servlet.http.HttpServletRequest;

/**
 * JCaptcha工具类
 * 提供相应的API来验证当前请求输入的验证码是否正确。
 * author : sunpanhu
 * createTime : 2018/4/18 上午11:22
 */
public class JCaptcha {
    //JCaptcha验证码图片生成引擎
    public static final MyManageableImageCaptchaService captchaService = new MyManageableImageCaptchaService(new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

    /**
     * 验证当前请求输入的验证码否正确；
     * 并从CaptchaService中删除已经生成的验证码
     * author : sunpanhu
     * createTime : 2018/4/18 上午11:26
     * @return
     */
    public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) {
            return false;
        }

        boolean validated = false;
        try {
            String id = request.getSession().getId();
            //用户输入的验证码错误时会返回false
            validated = captchaService.validateResponseForID(id, userCaptchaResponse).booleanValue();
        } catch (CaptchaServiceException e) {
            e.printStackTrace();
        }
        return validated;
    }

    /**
     * 验证当前请求输入的验证码是否正确；
     * 但不从CaptchaService中删除已经生成的验证码（比如Ajax验证时可以使用，防止多次生成验证码）
     * author : sunpanhu
     * createTime : 2018/4/18 上午11:26
     * @return
     */
    public static boolean hasCaptcha(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) return false;
        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.hasCapcha(id, userCaptchaResponse);
        } catch (CaptchaServiceException e) {
            e.printStackTrace();
        }
        return validated;
    }
}
