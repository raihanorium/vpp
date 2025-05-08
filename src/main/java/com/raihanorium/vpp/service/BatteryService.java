package com.raihanorium.vpp.service;

import com.raihanorium.vpp.web.dto.BatteryDto;
import com.raihanorium.vpp.web.request.GetBatteriesRequest;
import com.raihanorium.vpp.web.request.SaveBatteriesRequest;
import com.raihanorium.vpp.web.response.GetBatteriesResponse;

import java.util.List;

public interface BatteryService {
    List<BatteryDto> saveBatteries(SaveBatteriesRequest request);

    GetBatteriesResponse getBatteries(GetBatteriesRequest request);
}
