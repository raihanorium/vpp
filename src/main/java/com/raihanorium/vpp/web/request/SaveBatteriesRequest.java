package com.raihanorium.vpp.web.request;

import com.raihanorium.vpp.web.dto.BatteryDto;

import java.util.List;

public record SaveBatteriesRequest(
        List<BatteryDto> batteries
) {
}
