package com.raihanorium.vpp.service;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;

import java.util.List;

public interface BatteryService {
    List<BatteryDto> saveBatteries(SaveBatteriesRequest request);

    GetBatteriesResponse getBatteries(GetBatteriesRequest request);
}
