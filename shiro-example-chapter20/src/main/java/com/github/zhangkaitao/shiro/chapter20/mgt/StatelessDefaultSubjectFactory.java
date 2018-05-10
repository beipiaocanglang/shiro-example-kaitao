package com.github.zhangkaitao.shiro.chapter20.mgt;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Subject工厂
 * 通过调用context.setSessionCreationEnabled(false)表示不创建会话；
 * 如果之后再调用Subject.getSession()将会抛出DisabledSessionException异常。
 * author : sunpanhu
 * createTime : 2018/4/17 下午4:41
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session(会话)
        context.setSessionCreationEnabled(false);
        Subject subject = super.createSubject(context);
        return subject;
    }
}
