package com.hugh.crm.pojo;

public class Users {

    /*
    关于登录
        验证账号和密码
        User user = 执行SQL语句：select * from tbl_user where loginAct=? and loginPwd=?
        user对象为null，说明账号密码错误
        如果对象不为null，说明账号密码正确
        需要继续向下验证其它的字段信息
        从user中get到
        expireTime 验证失效时间
        lockState 验证锁定状态
        allowIps 验证浏览器的访问IP是否有效
     */
    private String id; // 编号， 主键
    private String loginAct; // 登陆账号
    private String name; // 真实姓名
    private String loginPwd; // 登陆密码
    private String email; // 邮箱
    private String expireTime; // 失效时间 19位
    private String lockState; // 锁定状态 0：锁定 1：启用
    private String deptNo; // 部门代号
    private String allowIps; // 允许访问的IP
    private String createTime; // 创建时间 19位
    private String createBy; // 创建人
    private String editTime; // 编辑时间 19位
    private String editBy; // 编辑人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}