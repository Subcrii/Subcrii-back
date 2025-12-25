package com.subcrii.subcrii.domain.member.entity;

public enum Role {
    USER, ADMIN;

    public String asAuthority() {
        return "ROLE_" + this.name();
    }
}