package com.clone.magabox.member.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole erole;

}
