package edu.hsbremen.cloud.persistance.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String authority;

    public RoleEntity() {
    }

    public static RoleEntity fromSecurityRole(String role) {
        final RoleEntity roleEntity = new RoleEntity();
        roleEntity.setAuthority(role);
        return roleEntity;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}