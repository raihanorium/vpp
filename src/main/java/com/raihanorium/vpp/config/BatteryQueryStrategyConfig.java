package com.raihanorium.vpp.config;

import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import com.raihanorium.vpp.repository.impl.DatabaseBasedBatteryRepositorySupportImpl;
import com.raihanorium.vpp.repository.impl.StreamBasedBatteryRepositorySupportImpl;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BatteryQueryStrategyConfig {

    @Value("${app.batteryQueryStrategy:database}")
    private String queryStrategy;

    @Nonnull private final DatabaseBasedBatteryRepositorySupportImpl databaseBasedBatteryRepositorySupport;
    @Nonnull private final StreamBasedBatteryRepositorySupportImpl streamBasedBatteryRepositorySupport;

    @Bean
    public BatteryRepositorySupport batteryRepositorySupport() {
        return "stream".equalsIgnoreCase(queryStrategy) ? streamBasedBatteryRepositorySupport : databaseBasedBatteryRepositorySupport;
    }
}
