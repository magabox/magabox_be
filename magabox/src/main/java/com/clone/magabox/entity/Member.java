package com.clone.magabox.entity;

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


//    public Member(MemberRequestDto requestDto, PasswordEncoder passwordEncoder) {
//        this.name = requestDto.getName();
//        this.username = requestDto.getUsername();
//        this.password = passwordEncoder.encode(requestDto.getPassword());
//     this.erole = ERole.ROLE_MEMBER;
//    }
}
