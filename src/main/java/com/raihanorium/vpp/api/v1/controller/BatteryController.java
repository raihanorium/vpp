package com.raihanorium.vpp.api.v1.controller;

import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.api.v1.response.SaveBatteriesResponse;
import com.raihanorium.vpp.service.BatteryService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/batteries")
public class BatteryController {

    @Nonnull private final BatteryService batteryService;

    @PostMapping
    public ResponseEntity<SaveBatteriesResponse> saveBatteries(@Valid @RequestBody SaveBatteriesRequest request) {
        return ResponseEntity.ok(batteryService.saveBatteries(request));
    }

    @GetMapping
    public ResponseEntity<GetBatteriesResponse> getBatteries(@Valid @RequestBody GetBatteriesRequest request) {
        return ResponseEntity.ok(batteryService.getBatteries(request));
    }
}
