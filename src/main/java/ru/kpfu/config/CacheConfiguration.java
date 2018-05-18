package ru.kpfu.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(ru.kpfu.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(ru.kpfu.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Doctor.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Speciality.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Speciality.class.getName() + ".doctors", jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Facility.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Facility.class.getName() + ".doctors", jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Patient.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Diseas.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Treatment.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Procedure.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Diagnosis.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Prescription.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.ProcedureOrder.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Drug.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.DrugOrder.class.getName(), jcacheConfiguration);
            cm.createCache(ru.kpfu.domain.Appointment.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
