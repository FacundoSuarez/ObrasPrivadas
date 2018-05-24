package municipalidad.obras.config;

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
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Plano.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Plano.class.getName() + ".planoDetalles", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.PlanoDetalle.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.PlanoDetalle.class.getName() + ".tramites", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Tramite.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Tramite.class.getName() + ".archivos", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Archivo.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Profesional.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Profesional.class.getName() + ".planos", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Profesional.class.getName() + ".contactos", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.ContactoProfesional.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.ContactoOperador.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.TipoContacto.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.TipoContacto.class.getName() + ".contactoOperadores", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.TipoContacto.class.getName() + ".contactoProfesionales", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Operador.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Operador.class.getName() + ".contactos", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Operador.class.getName() + ".tramites", jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.Oficina.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.TipoPlano.class.getName(), jcacheConfiguration);
            cm.createCache(municipalidad.obras.domain.TipoPlano.class.getName() + ".planoDetalles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
