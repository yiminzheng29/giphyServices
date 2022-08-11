package vttp.ssf.Day18ShoppingCart.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class GiphyRepository {

    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> temp;


    public Optional<String> get(String query) {

        ValueOperations<String, String> valueOps = temp.opsForValue();
        String payload = valueOps.get(query);

        if (payload==null) {
            return Optional.empty();
        }
        return Optional.of(payload);
        
    }

    public void save(String city, String payload) {

        ValueOperations<String, String> valueOps = temp.opsForValue();
        valueOps.set(city, payload);
    }
}
