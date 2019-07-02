package com.food.chicken.model.json;

import java.util.Date;

public class MySearchHistory {
    private String keyword;
    private Date lastSearchDate;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getLastSearchDate() {
        return lastSearchDate;
    }

    public void setLastSearchDate(Date lastSearchDate) {
        this.lastSearchDate = lastSearchDate;
    }

    @Override
    public String toString() {
        return "MySearchHistory{" +
                "keyword='" + keyword + '\'' +
                ", lastSearchDate=" + lastSearchDate +
                '}';
    }
}
