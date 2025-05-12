package com.raihanorium.vpp.service.impl;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.api.v1.response.SaveBatteriesResponse;
import com.raihanorium.vpp.repository.BatteryRepository;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import com.raihanorium.vpp.service.BatteryService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class BatteryServiceImpl implements BatteryService {

    @Nonnull private final BatteryRepository batteryRepository;
    @Nonnull private final BatteryRepositorySupport batteryRepositorySupport;
    @Nonnull private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public SaveBatteriesResponse saveBatteries(SaveBatteriesRequest request) {
        log.info("Saving batteries: {}", request);

        threadPoolTaskExecutor.execute(() -> {
            try {
                batteryRepository.saveAll(request.batteries().stream()
                        .map(BatteryDto::toEntity)
                        .toList());
            } catch (Exception e) {
                log.error("Error saving batteries: {}", e.getMessage(), e);
            } finally {
                log.info("Finished saving batteries");
            }
        });
        return SaveBatteriesResponse.builder()
                .message("Batteries are being saved in the background")
                .build();
    }

    @Override
    public GetBatteriesResponse getBatteries(GetBatteriesRequest request) {
        log.info("Getting batteries with request: {}", request);

        return batteryRepositorySupport.findAll(request);
    }

    @Override
    public void deleteAllBatteries() {
        batteryRepository.deleteAll();
    }
}
