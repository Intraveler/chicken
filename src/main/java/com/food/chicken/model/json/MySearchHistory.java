package com.food.chicken.model.json;

public class MySearchHistory {
    private String keyword;
    private String lastSearchDate;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLastSearchDate() {
        return lastSearchDate;
    }

    public void setLastSearchDate(String lastSearchDate) {
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
