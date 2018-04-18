package com.github.zhangkaitao.shiro.chapter22.jcaptcha;

import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 判断仓库中是否有相应的验证码存在
 * author : sunpanhu
 * createTime : 2018/4/18 上午11:21
 */
public class MyManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

    public MyManageableImageCaptchaService(CaptchaStore captchaStore, com.octo.captcha.engine.CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCapcha(String id, String userCaptchaResponse) {
        Boolean aBoolean = store.getCaptcha(id).validateResponse(userCaptchaResponse);
        return aBoolean;
    }
}
