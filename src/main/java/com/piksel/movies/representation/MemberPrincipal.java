package com.piksel.movies.representation;

/**
 * Created by dino on 9/19/16.
 */
public class MemberPrincipal {

    private long memberId;

    public MemberPrincipal() {
    }

    public MemberPrincipal(long memberId) {
        this.memberId = memberId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "MemberPrincipal{" +
                "memberId=" + memberId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberPrincipal that = (MemberPrincipal) o;

        return memberId == that.memberId;

    }

    @Override
    public int hashCode() {
        return (int) (memberId ^ (memberId >>> 32));
    }
}
