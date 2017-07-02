package com.shang.zuul.repository;

import com.shang.zuul.domain.URLEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shangzebei on 2017/6/30.
 */
public interface URLEntryRepository extends JpaRepository<URLEntry,Long>{
    URLEntry findByTitle(String url);
}
