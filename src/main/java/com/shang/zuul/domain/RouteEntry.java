package com.shang.zuul.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
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
public class RouteEntry {

    @Id @GeneratedValue
    private Long id;
    /**
     * The service ID (if any) to map to this route. You can specify a physical URL or
     * a service, but not both.
     */
    private String serviceId;
    @NotNull
    @NotEmpty
    private String title;
    /**
     * A full physical URL to map to the route. An alternative is to use a service ID
     * and service discovery to find the physical address.
     */
    @NotNull
    @NotEmpty
    private String url;
    /**
     * The path (pattern) for the route, e.g. /foo/**.
     */
    private String path;
    /**
     * Flag to determine whether the prefix for this route (the path, minus pattern
     * patcher) should be stripped before forwarding.
     */
    @ColumnDefault(value = "true")
    private boolean stripPrefix;



}
