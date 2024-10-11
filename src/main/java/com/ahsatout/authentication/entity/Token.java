package com.ahsatout.authentication.entity;

import com.ahsatout.authentication.entity.enums.TokenType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
public class Token {
    //Expiration time 10 miutes
    private static  final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Token(User user, String token, TokenType type) {
        super();
        this.token = token;
        this.user = user;
        this.tokenType = type;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public Token(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

}

