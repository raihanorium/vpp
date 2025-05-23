package com.raihanorium.vpp.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.persistence.Battery;
import com.raihanorium.vpp.persistence.QBattery;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseBasedBatteryRepositorySupportImpl extends QuerydslRepositorySupport implements BatteryRepositorySupport {

    public DatabaseBasedBatteryRepositorySupportImpl() {
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

        List<String> batteryNames = from(battery)
                .select(battery.name)
                .where(predicate)
                .orderBy(battery.name.asc())
                .fetch();

        Tuple capacityStats = from(battery)
                .select(battery.capacity.sum(), battery.capacity.avg())
                .where(predicate)
                .fetchOne();

        long total = 0L;
        double average = 0.0;

        if (capacityStats != null) {
            Number sum = capacityStats.get(battery.capacity.sum());
            Number avg = capacityStats.get(battery.capacity.avg());

            total = sum != null ? sum.longValue() : 0L;
            average = avg != null ? avg.doubleValue() : 0.0;
        }

        return GetBatteriesResponse.builder()
                .batteryNames(batteryNames)
                .totalWattage(total)
                .averageWattage(average)
                .build();
    }

}
