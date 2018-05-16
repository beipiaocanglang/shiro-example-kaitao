package com.github.zhangkaitao.shiro.chapter22.web.bind.annotation;

import com.github.zhangkaitao.shiro.chapter22.Constants;
import java.lang.annotation.*;

/**
 * 绑定当前登录的用户
 * 不同于@ModelAttribute
 * author : sunpanhu
 * createTime : 2018/5/16 上午10:16
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default Constants.CURRENT_USER;
}
