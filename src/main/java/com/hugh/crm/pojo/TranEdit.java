package com.hugh.crm.pojo;

public class TranEdit {
    private String id;
    private String owner;
    private String money;	//交易金额
    private String name;	//交易名称
    private String expectedDate;	//预计成交日期
    private String customerId;
    private String customerName;
    private String stage;	//交易阶段
    private String type;	//交易类型
    private String percent; //交易可能性
    private String source;	//交易来源
    private String activityId;
    private String activityName;
    private String contactsId;
    private String contactsName;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;	//联系纪要
    private String nextContactTime;	//下次联系时间

    public TranEdit() {
    }

    public TranEdit(String id, String owner, String money, String name, String expectedDate, String customerId, String customerName, String stage, String type, String percent, String source, String activityId, String activityName, String contactsId, String contactsName, String createBy, String createTime, String editBy, String editTime, String description, String contactSummary, String nextContactTime) {
        this.id = id;
        this.owner = owner;
        this.money = money;
        this.name = name;
        this.expectedDate = expectedDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.stage = stage;
        this.type = type;
        this.percent = percent;
        this.source = source;
        this.activityId = activityId;
        this.activityName = activityName;
        this.contactsId = contactsId;
        this.contactsName = contactsName;
        this.createBy = createBy;
        this.createTime = createTime;
        this.editBy = editBy;
        this.editTime = editTime;
        this.description = description;
        this.contactSummary = contactSummary;
        this.nextContactTime = nextContactTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactSummary() {
        return contactSummary;
    }

    public void setContactSummary(String contactSummary) {
        this.contactSummary = contactSummary;
    }

    public String getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(String nextContactTime) {
        this.nextContactTime = nextContactTime;
    }
}
