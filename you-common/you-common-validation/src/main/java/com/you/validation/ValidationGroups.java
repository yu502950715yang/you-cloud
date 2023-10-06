package com.you.validation;

/**
 * 校验分组
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2019-11-26 14:12
 */
public class ValidationGroups {

    /**
     * 公共
     */
    public interface Common {
    }

    /**
     * 新增
     */
    public interface Add extends Common {
    }

    /**
     * 编辑
     */
    public interface Update extends Common {
    }

    /**
     * 查询
     */
    public interface Query extends Common {
    }

    /**
     * 其他
     */
    public interface Other {
    }
}
