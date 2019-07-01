package com.food.chicken.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MEMBER")
public class Member {

    private long memberUid;
    private String password;
    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_UID")
    public long getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(long memberUid) {
        this.memberUid = memberUid;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberUid == member.memberUid &&
                Objects.equals(password, member.password) &&
                Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberUid, password, id);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberUid=" + memberUid +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}