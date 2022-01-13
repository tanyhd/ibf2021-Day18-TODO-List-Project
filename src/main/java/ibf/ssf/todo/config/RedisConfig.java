package ibf.ssf.todo.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase; 

    //@Value("${spring.redis.password}")
    //private String redisPassword

    private final Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Bean("My Template")
    public RedisTemplate<String, String> createRedisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setDatabase(redisDatabase);

        final String redisPassword = System.getenv("REDIS_PASSWORD");
        if(null != redisPassword) {
            logger.log(Level.INFO, "Setting Redis password");
            config.setPassword(redisPassword);
        }

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;

    }
    
}
