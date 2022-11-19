package com.learning.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
//@ComponentScan("olivecrypto.upi.redis.streams.redis")
@EnableScheduling
//@EnableRedisEnhancedRepositories("olivecrypto.upi.redis.streams.redis.dao")
//@RequiredArgsConstructor
public class RedisConfig {
	
//	 @Autowired
//	    RedisMessageSubscriber redisMessageSubscriber;
	 
	 @Value("${redis.host:localhost}")
		private String host;
	 
	 @Value("${redis.port:6379}")
		private String port;
	 
//	 @Value("${stream.key}")
//		private String streamKey;

	 

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
        System.out.println("jost"+host);
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
//		redisStandaloneConfiguration.setHostName("localhost");
		redisStandaloneConfiguration.setPort(Integer.parseInt(port));
//		redisStandaloneConfiguration.setPort(6379);
		redisStandaloneConfiguration.setDatabase(0);
		// Note: Uncomment the below line if any password has been set
//		 redisStandaloneConfiguration.setPassword(RedisPassword.of("WHUAybGgYWjRZOqa7T16cBI2zSdbua9C"));

		JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
		jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
				jedisClientConfiguration.build());

		return jedisConFactory;
	}
	
	

    

	@Bean(name="redisConnectionTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
	
	@Bean
	@Primary
	ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		return new LettuceConnectionFactory(host,Integer.parseInt(port));
	}
	
	//@Bean(name="reactiveStreamTemplate")
	@Primary
	ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
	  JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
	  StringRedisSerializer stringRedisSerializer = StringRedisSerializer.UTF_8;
	  GenericToStringSerializer<String> longToStringSerializer = new GenericToStringSerializer<>(String.class);
	  ReactiveRedisTemplate<String, String> template = new ReactiveRedisTemplate<>(factory,
	      RedisSerializationContext.<String, String>newSerializationContext(jdkSerializationRedisSerializer)
	          .key(stringRedisSerializer).value(longToStringSerializer).build());
	  return template;
	}
}