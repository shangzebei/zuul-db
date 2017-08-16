package com.shang.zuul.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by shangzebei on 2017/8/16.
 */
@Data
public class Message implements Serializable{
    private int type;
    private String message;

    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message() {
    }
}
