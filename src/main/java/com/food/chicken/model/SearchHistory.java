package com.food.chicken.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "SEARCH_HISTORY_GEN", sequenceName = "SEARCH_HISTORY_SEQ", initialValue = 1, allocationSize = 1)
@Table(name = "SEARCH_HISTORY")
@IdClass(SearchHistoryPK.class)
public class SearchHistory implements Serializable {

    private static final long serialVersionUID = -3840852200800769301L;
    private long historyUid;
    private long memberUid;
    private String keyword;
    private Date createdDate;
    private Date lastSearchDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEARCH_HISTORY_GEN")
    @Column(name = "HISTORY_UID")
    public long getHistoryUid() {
        return historyUid;
    }

    public void setHistoryUid(long historyUid) {
        this.historyUid = historyUid;
    }

    @Id
    @Column(name = "MEMBER_UID")
    public long getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(long memberUid) {
        this.memberUid = memberUid;
    }

    @Id
    @Column(name = "KEYWORD")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "LAST_SEARCH_DATE")
    public Date getLastSearchDate() {
        return lastSearchDate;
    }

    public void setLastSearchDate(Date lastSearchDate) {
        this.lastSearchDate = lastSearchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistory that = (SearchHistory) o;
        return historyUid == that.historyUid &&
                memberUid == that.memberUid &&
                Objects.equals(keyword, that.keyword) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastSearchDate, that.lastSearchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyUid, memberUid, keyword, createdDate, lastSearchDate);
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "historyUid=" + historyUid +
                ", memberUid=" + memberUid +
                ", keyword='" + keyword + '\'' +
                ", createdDate=" + createdDate +
                ", lastSearchDate=" + lastSearchDate +
                '}';
    }
}