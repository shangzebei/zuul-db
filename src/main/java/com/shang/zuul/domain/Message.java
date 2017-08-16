package com.shang.zuul.domain;

import lombok.Data;

/**
 * Created by shangzebei on 2017/8/16.
 */
@Data
public class Message {
    private int type;
    private String message;

    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message() {
    }
}
