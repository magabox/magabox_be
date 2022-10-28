package com.clone.magabox.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(nullable = false)
    private ERole erole;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

//    public Member(MemberRequestDto requestDto, PasswordEncoder passwordEncoder) {
//        this.name = requestDto.getName();
//        this.username = requestDto.getUsername();
//        this.password = passwordEncoder.encode(requestDto.getPassword());
//     this.erole = ERole.ROLE_MEMBER;
//    }
}
