package com.raihanorium.vpp.api.v1.request;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SaveBatteriesRequest(

        @NotEmpty(message = "{batteries.not.empty}")
        @Valid
        List<BatteryDto> batteries
) {
}
