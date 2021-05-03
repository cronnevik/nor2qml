package no.nnsn.quakemlwebserviceingestorexecutable.config;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@org.springframework.context.annotation.Configuration
public class FlexyPoolConfiguration {
    @Autowired
    private HikariDataSource poolingDataSource;

    private String uniqueId = UUID.randomUUID().toString();

    @Bean
    public Configuration<HikariDataSource> configuration() {
        return new Configuration.Builder<HikariDataSource>(
                uniqueId,
                poolingDataSource,
                HikariCPPoolAdapter.FACTORY
        ).build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource dataSource() {
        Configuration<HikariDataSource> configuration = configuration();
        return new FlexyPoolDataSource<HikariDataSource>(configuration,
                new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
                new RetryConnectionAcquiringStrategy.Factory(2)
        );
    }
}
