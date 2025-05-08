package com.raihanorium.vpp.web.controller;

import com.raihanorium.vpp.service.BatteryService;
import com.raihanorium.vpp.web.dto.BatteryDto;
import com.raihanorium.vpp.web.request.GetBatteriesRequest;
import com.raihanorium.vpp.web.request.SaveBatteriesRequest;
import com.raihanorium.vpp.web.response.GetBatteriesResponse;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/batteries")
public class BatteryController {

    @Nonnull private final BatteryService batteryService;

    @PostMapping
    public ResponseEntity<List<BatteryDto>> saveBatteries(@RequestBody SaveBatteriesRequest request) {
        return ResponseEntity.ok(batteryService.saveBatteries(request));
    }

    @GetMapping
    public ResponseEntity<GetBatteriesResponse> getBatteries(@RequestBody GetBatteriesRequest request) {
        return ResponseEntity.ok(batteryService.getBatteries(request));
    }
}
