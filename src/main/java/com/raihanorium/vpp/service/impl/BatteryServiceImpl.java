package com.raihanorium.vpp.service.impl;

import com.raihanorium.vpp.persistence.Battery;
import com.raihanorium.vpp.repository.BatteryRepository;
import com.raihanorium.vpp.service.BatteryService;
import com.raihanorium.vpp.web.dto.BatteryDto;
import com.raihanorium.vpp.web.request.GetBatteriesRequest;
import com.raihanorium.vpp.web.request.SaveBatteriesRequest;
import com.raihanorium.vpp.web.response.GetBatteriesResponse;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BatteryServiceImpl implements BatteryService {

    @Nonnull private final BatteryRepository batteryRepository;

    @Override
    public List<BatteryDto> saveBatteries(SaveBatteriesRequest request) {
        return batteryRepository.saveAll(request.batteries().stream()
                        .map(BatteryDto::toEntity)
                        .toList()).stream()
                .map(BatteryDto::fromEntity)
                .toList();
    }

    @Override
    public GetBatteriesResponse getBatteries(GetBatteriesRequest request) {
        List<Battery> batteries = batteryRepository.findAllByPostcodeInOrderByName(request.postcodes());
        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .toList();
        long totalWattage = batteries.stream()
                .mapToLong(Battery::getCapacity)
                .sum();
        double averageWattage = batteries.stream()
                .mapToInt(Battery::getCapacity)
                .average()
                .orElse(0);

        return new GetBatteriesResponse(batteryNames, totalWattage, averageWattage);
    }
}
