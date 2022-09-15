package com.ciandt.summit.bootcamp2022.config.cache;

import lombok.Generated;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
@Generated
public class CacheConfig {
    @CacheEvict(allEntries = true, value = {"searchMusic"})
    @Scheduled(fixedDelay = 10000)
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("searchMusic");
    }
}