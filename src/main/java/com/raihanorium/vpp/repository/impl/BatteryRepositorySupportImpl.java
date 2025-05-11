package com.raihanorium.vpp.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.persistence.Battery;
import com.raihanorium.vpp.persistence.QBattery;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BatteryRepositorySupportImpl extends QuerydslRepositorySupport implements BatteryRepositorySupport {
    public BatteryRepositorySupportImpl() {
        super(Battery.class);
    }

    @Override
    public GetBatteriesResponse findAll(GetBatteriesRequest request) {
        QBattery battery = QBattery.battery;

        JPQLQuery<Battery> baseQuery = from(battery)
                .where(battery.postcode.in(request.postcodes()));

        if (request.minimumCapacity() != null) {
            baseQuery.where(battery.capacity.goe(request.minimumCapacity()));
        }
        if (request.maximumCapacity() != null) {
            baseQuery.where(battery.capacity.loe(request.maximumCapacity()));
        }

        List<String> batteryNames = baseQuery
                .select(battery.name)
                .orderBy(battery.name.asc())
                .fetch();

        Tuple capacityStats = baseQuery
                .select(
                        battery.capacity.sum(),
                        battery.capacity.avg())
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
