package com.raihanorium.vpp.api.v1.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetBatteriesResponse(
        List<String> batteryNames,
        long totalWattage,
        double averageWattage
) {
}
