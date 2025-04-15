package no.nnsn.ingestor.config;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@org.springframework.context.annotation.Configuration
public class FlexyPoolConfiguration {
    private final HikariDataSource poolingDataSource;

    private final String uniqueId = UUID.randomUUID().toString();

    public FlexyPoolConfiguration(HikariDataSource poolingDataSource) {
        this.poolingDataSource = poolingDataSource;
    }

    @Bean
    public Configuration<HikariDataSource> configuration() {
        return new Configuration.Builder<HikariDataSource>(
                uniqueId,
                poolingDataSource,
                HikariCPPoolAdapter.FACTORY
        ).build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource<HikariDataSource> dataSource() {
        Configuration<HikariDataSource> configuration = configuration();
        return new FlexyPoolDataSource<HikariDataSource>(configuration,
                new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
                new RetryConnectionAcquiringStrategy.Factory(2)
        );
    }
}
