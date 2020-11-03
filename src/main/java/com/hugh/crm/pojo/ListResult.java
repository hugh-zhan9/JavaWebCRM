package com.hugh.crm.pojo;

import java.util.List;

public class ListResult<T> {
    private Integer total;
    private List<T> listReuslt;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getListReuslt() {
        return listReuslt;
    }

    public void setListReuslt(List<T> listReuslt) {
        this.listReuslt = listReuslt;
    }
}
