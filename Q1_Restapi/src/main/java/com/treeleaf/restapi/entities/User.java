package com.treeleaf.restapi.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Collection<? extends GrantedAuthority> getRoles() {
        return new ArrayList<>() {
            {
                add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return role.name();
                    }
                });
            }
        };
    }
}
