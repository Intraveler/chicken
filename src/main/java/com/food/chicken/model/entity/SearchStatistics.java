package com.food.chicken.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SEARCH_STATISTICS")
public class SearchStatistics implements Serializable {

    private static final long serialVersionUID = -416161738284155127L;
    private String keyword;
    private int count;

    @Id
    @Column(name = "KEYWORD")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column(name = "COUNT")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchStatistics that = (SearchStatistics) o;
        return count == that.count &&
                Objects.equals(keyword, that.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, count);
    }

    @Override
    public String toString() {
        return "SearchStatistics{" +
                "keyword='" + keyword + '\'' +
                ", count=" + count +
                '}';
    }
}