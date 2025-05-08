package com.raihanorium.vpp.web.response;

import java.util.List;

public record GetBatteriesResponse(
        List<String> batteryNames,
        Long totalWattage,
        double averageWattage
) {
}
