package com.raihanorium.vpp.web.dto;

import com.raihanorium.vpp.persistence.Battery;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BatteryDto(
        Long id,
        @NotBlank(message = "{battery.name.not.blank}")
        String name,
        @NotBlank(message = "{battery.postcode.not.blank}")
        String postcode,
        @Min(value = 1, message = "{battery.capacity.min}")
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
