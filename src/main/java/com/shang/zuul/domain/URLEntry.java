package com.shang.zuul.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by shangzebei on 2017/6/30.
 */
@Entity
@Data
public class URLEntry {

    @Id @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    private String url;
    @NotNull
    @NotEmpty
    private String local;
    private String path;

    public URLEntry() {
    }

}
