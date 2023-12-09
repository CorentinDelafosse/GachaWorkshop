package App.BD;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int REDIS_PORT = 6379;

    public static JedisPool initRedisConnectionPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // Configurer le pool si nécessaire

        return new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
    }
}
