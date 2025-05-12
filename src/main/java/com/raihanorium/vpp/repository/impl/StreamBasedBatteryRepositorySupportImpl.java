package com.raihanorium.vpp.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.persistence.Battery;
import com.raihanorium.vpp.persistence.QBattery;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StreamBasedBatteryRepositorySupportImpl extends QuerydslRepositorySupport implements BatteryRepositorySupport {

    public StreamBasedBatteryRepositorySupportImpl() {
        super(Battery.class);
    }

    @Override
    public GetBatteriesResponse findAll(GetBatteriesRequest request) {
        QBattery battery = QBattery.battery;

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(battery.postcode.in(request.postcodes()));

        if (request.minimumCapacity() != null) {
            predicate.and(battery.capacity.goe(request.minimumCapacity()));
        }
        if (request.maximumCapacity() != null) {
            predicate.and(battery.capacity.loe(request.maximumCapacity()));
        }

        List<Battery> batteries = from(battery)
                .select(battery)
                .where(predicate)
                .orderBy(battery.name.asc())
                .fetch();

        return GetBatteriesResponse.builder()
                .batteryNames(batteries.stream().map(Battery::getName).sorted().toList())
                .totalWattage(batteries.stream().mapToLong(Battery::getCapacity).sum())
                .averageWattage(batteries.stream().mapToLong(Battery::getCapacity).average().orElse(0.0))
                .build();

    }

}
