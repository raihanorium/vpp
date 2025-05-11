package com.raihanorium.vpp.repository;

import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryRepositorySupport {

    GetBatteriesResponse findAll(GetBatteriesRequest request);
}
