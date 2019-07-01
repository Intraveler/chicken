package com.food.chicken.model;

import java.io.Serializable;
import java.util.Objects;

public class SearchHistoryPK implements Serializable {

    private static final long serialVersionUID = 1697965185885060201L;
    private long historyUid;

    private long memberUid;
    private String keyword;

    public SearchHistoryPK(){}

    public SearchHistoryPK(long historyUid, long memberUid, String keyword){
        this.historyUid = historyUid;
        this.memberUid = memberUid;
        this.keyword = keyword;

    }

    public long getHistoryUid() {
        return historyUid;
    }

    public void setHistoryUid(long historyUid) {
        this.historyUid = historyUid;
    }

    public long getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(long memberUid) {
        this.memberUid = memberUid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistoryPK that = (SearchHistoryPK) o;
        return historyUid == that.historyUid &&
                memberUid == that.memberUid &&
                Objects.equals(keyword, that.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyUid, memberUid, keyword);
    }

    @Override
    public String toString() {
        return "SearchHistoryPK{" +
                "historyUid=" + historyUid +
                ", memberUid=" + memberUid +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}