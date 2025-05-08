package com.raihanorium.vpp.controller;

import com.raihanorium.vpp.service.BatteryService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BatteryController {

    @Nonnull private final BatteryService batteryService;

}
