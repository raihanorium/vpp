package com.raihanorium.vpp.web.dto;

import com.raihanorium.vpp.persistence.Battery;
import lombok.Builder;

@Builder
public record BatteryDto(
        Long id,
        String name,
        String postcode,
        int capacity
) {
    public static Battery toEntity(BatteryDto batteryDto) {
        return new Battery(
                batteryDto.id,
                batteryDto.name,
                batteryDto.postcode,
                batteryDto.capacity
        );
    }

    public static BatteryDto fromEntity(Battery battery) {
        return BatteryDto.builder()
                .id(battery.getId())
                .name(battery.getName())
                .postcode(battery.getPostcode())
                .capacity(battery.getCapacity())
                .build();
    }
}
