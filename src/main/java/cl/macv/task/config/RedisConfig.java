package cl.macv.task.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration // Indica que es una clase de configuración de Spring
@EnableCaching // ACTIVA el sistema de caché de Spring
public class RedisConfig {

    /* Configuración de la conexión a Redis 
    * creación de la fabrica de conexiones a redis
    * **Lettuce** es el cliente Redis (asíncrono, thread-safe)
    * Spring usa esta fábrica para obtener conexiones cuando necesita hablar con Redis
    */
    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    /* Configuración del CacheManager para Redis
    * El **CacheManager** es el componente central que Spring usa para manejar el caché
    * Recibe la `connectionFactory` para conectarse a Redis
 */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        /* **ObjectMapper**: Convierte objetos Java ↔ JSON
        * **activateDefaultTyping**: Incluye `@class` en el JSON para saber qué tipo deserializar
        * Sin esto, al leer del caché, Jackson no sabe si es `TaskResponse` o `LinkedHashMap` */
        ObjectMapper objectMapper = JsonMapper.builder()
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(Object.class)
                    .build(),
                DefaultTyping.NON_FINAL
            )
            .build();

        /* - **entryTtl(10 min)**: Time-To-Live, después de 10 minutos el dato se borra automáticamente
        * **serializeKeysWith(StringRedisSerializer)**: Keys como texto simple (`tasks::1`)
        * **serializeValuesWith(GenericJacksonJsonRedisSerializer)**: Valores como JSON */
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJacksonJsonRedisSerializer(objectMapper)));

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

}
