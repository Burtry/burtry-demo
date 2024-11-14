package icu.burtry.writespaceutils.thread;

import icu.burtry.writespacemodel.entity.Admin;

public class AdminThreadLocalUtil {

    private final static ThreadLocal<Admin> ADMIN_THREAD_LOCAL = new ThreadLocal<>();

    //存入线程
    public static void setAdminThreadLocal(Admin admin) {
        ADMIN_THREAD_LOCAL.set(admin);
    }

    //获取
    public static Admin getAdmin() {
        return ADMIN_THREAD_LOCAL.get();
    }

    //清理
    public static void clear() {
        ADMIN_THREAD_LOCAL.remove();
    }
}
