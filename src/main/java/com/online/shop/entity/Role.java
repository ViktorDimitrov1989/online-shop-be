package com.online.shop.entity;

import com.online.shop.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType authority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleType authority) {
        this.authority = authority;
    }
}
