package com.studentcardsapi.model.user;

import com.studentcardsapi.enums.AppUserRole;
import com.studentcardsapi.model.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AppUser extends GenericModel implements UserDetails {

    private String name;

    private String cpf;

    // e-mail
    @EqualsAndHashCode.Include
    private String username;

    private String password;

    private Boolean locked = false;

    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private AppUserRole userRole;

    private String recoveryPasswordToken;

    private Date recoveryPasswordTokenExpiration;

    private Date creationDate;

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getClass().getName().toUpperCase());
        return Collections.singletonList(authority);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
