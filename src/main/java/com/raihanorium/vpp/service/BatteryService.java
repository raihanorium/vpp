package com.raihanorium.vpp.service;

import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.api.v1.response.SaveBatteriesResponse;

public interface BatteryService {
    SaveBatteriesResponse saveBatteries(SaveBatteriesRequest request);

    GetBatteriesResponse getBatteries(GetBatteriesRequest request);

    void deleteAllBatteries();
}
