package com.shang.zuul.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Entity
@Data
public class URLEntry {

    @Id @GeneratedValue
    private Long id;
    private String url;
    private String local;


}
