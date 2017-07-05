package com.shang.zuul.repository;

import com.shang.zuul.domain.RouteEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shangzebei on 2017/6/30.
 */
public interface URLEntryRepository extends JpaRepository<RouteEntry,Long>{
    RouteEntry findByTitle(String url);

    void deleteByTitle(String title);
}
