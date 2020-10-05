package com.jn.agileway.redis.counter;

import com.jn.agileway.redis.redistemplate.IredisTemplate;

public class HashedDistributedCounter implements DistributedCounter {
    private IredisTemplate redisTemplate;
    private String key;
    private String counterKey; // the key in a hash

    public HashedDistributedCounter(IredisTemplate redisTemplate, String key, String counterKey){
        setRedisTemplate(redisTemplate);
        setKey(key);
        setCounterKey(counterKey);
    }

    @Override
    public Long increment() {
        return this.redisTemplate.opsForHash().increment(this.key, counterKey,1);
    }

    @Override
    public Long increment(Long aLong) {
        return this.redisTemplate.opsForHash().increment(this.key, counterKey, aLong);
    }

    @Override
    public Long decrement() {
        return this.redisTemplate.opsForHash().increment(this.key, counterKey, -1);
    }

    @Override
    public Long decrement(Long aLong) {
        return this.redisTemplate.opsForHash().increment(this.key, counterKey, -aLong);
    }

    @Override
    public Long get() {
        return (Long)this.redisTemplate.opsForHash().get(this.key, counterKey);
    }

    @Override
    public void set(Long aLong) {
        this.redisTemplate.opsForHash().put(this.key, counterKey, aLong);
    }

    @Override
    public void clear() {
        this.redisTemplate.opsForHash().delete(this.key, counterKey);
    }

    @Override
    public IredisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    @Override
    public void setRedisTemplate(IredisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCounterKey() {
        return this.counterKey;
    }

    @Override
    public void setCounterKey(String key) {
        this.counterKey = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
}
