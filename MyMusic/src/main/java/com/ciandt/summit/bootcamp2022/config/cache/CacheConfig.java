package com.ciandt.summit.bootcamp2022.config.cache;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("searchMusic");

        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {"searchMusic"})
    @Scheduled(fixedDelay = 10000)
    public void reportCacheEvict() {
        System.out.println("Flush Cache ");
    }
}
