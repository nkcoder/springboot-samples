package org.nkcoder.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@Configuration
@ConfigurationProperties(prefix = "cache")
//@PropertySource(value = "classpath:application.yml")
@Data
public class CacheConfiguration {

  public static final String CACHE_ONE = "one";
  public static final String CACHE_TWO = "two";

  @Data
  public static class CacheSpec {

    private Integer expireAfterWrite;
    private Integer maximumSize;
  }

  private Map<String, CacheSpec> specs;

  @Bean
  public CacheManager cacheManager(Ticker ticker) {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    if (!CollectionUtils.isEmpty(specs)) {
      List<CaffeineCache> caches = specs.entrySet().stream()
          .map(entry -> buildCache(entry.getKey(), entry.getValue(), ticker))
          .collect(Collectors.toList());
      cacheManager.setCaches(caches);
    }
    return cacheManager;
  }


  @Bean
  public Ticker ticker() {
    return Ticker.systemTicker();
  }

  private CaffeineCache buildCache(String name, CacheSpec cacheSpec, Ticker ticker) {
    System.out.println("hello....");
    Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder()
        .expireAfterWrite(cacheSpec.getExpireAfterWrite(), TimeUnit.SECONDS)
        .maximumSize(cacheSpec.getMaximumSize())
        .ticker(ticker)
        .recordStats();
    return new CaffeineCache(name, cacheBuilder.build());
  }

}
