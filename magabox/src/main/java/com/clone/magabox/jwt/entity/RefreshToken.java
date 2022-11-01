package com.clone.magabox.jwt.entity;

import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
