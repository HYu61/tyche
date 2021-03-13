package pers.hyu.tyche.enums;

/**
 * The enum contains the keys and the expired time for the key that stored in redis.
 *
 * @author Heng Yu
 * @version 1.0
 */
public enum RedisKeyEnum {
    USER_SESSION_TOKEN_KEY("user-redis-session", 1200),
    HOT_SEARCH_KEY("hot-search",-1);
    private String key;
    private long expiredTimeInSecond;

    RedisKeyEnum(String key, long expiredTimeInSecond) {
        this.key = key;
        this.expiredTimeInSecond = expiredTimeInSecond;
    }

    public String getKey() {
        return key;
    }

    public long getExpiredTimeInSecond() {
        return expiredTimeInSecond;
    }
}
