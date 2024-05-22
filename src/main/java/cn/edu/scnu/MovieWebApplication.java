package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.FlushMode;
import org.springframework.session.MapSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds =
        MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS,   //半小时有效期
        redisNamespace = "spring:session",                   //redis中存储的key前缀
        flushMode = FlushMode.ON_SAVE
)
@EnableCaching
@SpringBootApplication
public class MovieWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieWebApplication.class, args);
    }

}
