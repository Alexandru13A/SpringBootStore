package com.alexandru.SpringBootStore.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "user_role")
    private String userRole;

    @OneToOne(mappedBy = "role")
    private User user;


}
