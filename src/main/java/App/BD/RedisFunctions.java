package App.BD;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisFunctions {

    private static final JedisPool jedisPool = RedisConfig.initRedisConnectionPool();

    public static void saveSearchResult(String result, String titre, String contenu, String prix, String id,
            String image) {
        try (Jedis jedis = jedisPool.getResource()) {
            // Utilisez la clé "search:" suivie de l'identifiant comme préfixe
            String key = "search:" + result;

            // Ajoutez les informations de l'article à la liste dans Redis
            jedis.rpush(key, id, image, titre, contenu, prix);

            // Définissez une expiration (par exemple, 1 heure) si nécessaire
            jedis.expire(key, 3600);
        }
    }

    public static List<String> getSearchResult(String searchTerm) {
        try (Jedis jedis = jedisPool.getResource()) {
            // Utilisez la clé "search:" suivie du terme de recherche comme préfixe
            String key = "search:" + searchTerm;

            return jedis.lrange(key, 0, -1);

        }
    }

    public static void deleteSearchResult(String searchTerm) {
        try (Jedis jedis = jedisPool.getResource()) {
            // Utilisez la clé "search:" suivie du terme de recherche comme préfixe
            String key = "search:" + searchTerm;

            // Supprimez le résultat de Redis
            jedis.del(key);
        }
    }
}
