package icu.burtry.writespaceutils.thread;

import icu.burtry.writespacemodel.entity.User;

public class UserThreadLocalUtil {

    private final static ThreadLocal<User> ADMIN_THREAD_LOCAL = new ThreadLocal<>();

    //存入线程
    public static void setUserThreadLocal(User user) {
        ADMIN_THREAD_LOCAL.set(user);
    }

    //获取
    public static User getUser() {
        return ADMIN_THREAD_LOCAL.get();
    }

    //清理
    public static void clear() {
        ADMIN_THREAD_LOCAL.remove();
    }
}
