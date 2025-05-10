package com.raihanorium.vpp.web.request;

import com.raihanorium.vpp.web.dto.BatteryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SaveBatteriesRequest(

        @NotEmpty(message = "{batteries.not.empty}")
        @Valid
        List<BatteryDto> batteries
) {
}
