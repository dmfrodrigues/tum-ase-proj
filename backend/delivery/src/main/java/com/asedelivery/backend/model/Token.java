package com.asedelivery.backend.model;

import java.security.SecureRandom;
import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("token")
public class Token {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM = UPPER + LOWER + DIGITS;
    private static final String SYMBOLS = ALPHANUM;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int DEFAULT_SIZE = 64;

    static public enum Type {
        RFID
    };

    private Type type;

    @Id
    private String id;

    @DocumentReference
    public Principal principal;

    public Token(Type type, Principal principal, String id){
        this.type = type;
        this.principal = principal;
        this.id = id;
    }

    static public Token generateToken(Type type, Principal principal, int tokenSize){
        return new Token(type, principal, generateTokenId(tokenSize));
    }

    static public Token generateToken(Type type, Principal principal){
        return generateToken(type, principal, DEFAULT_SIZE);
    }

    public String getId(){
        return id;
    }

    public Type getType(){
        return type;
    }

    static public String generateTokenId(int size){
        char[] buf = new char[size];
        for(int i = 0; i < size; ++i)
            buf[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        return new String(buf);
    }
}
