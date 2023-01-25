package com.hermes.ithermes.domain.entity;

import java.io.Serializable;

public interface UserDetails extends Serializable {
    String getPassword();
    String getUsername();
    boolean isAccountNotExpired();
    boolean isAccountNonLocked();
    boolean isCredentialNonExpired();
    boolean isEnabled();
}
