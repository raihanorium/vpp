package com.raihanorium.vpp.api.v1.response;

import java.util.List;

public record GetBatteriesResponse(
        List<String> batteryNames,
        Long totalWattage,
        double averageWattage
) {
}
