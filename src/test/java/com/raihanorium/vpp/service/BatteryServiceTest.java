package com.raihanorium.vpp.service;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.api.v1.response.SaveBatteriesResponse;
import com.raihanorium.vpp.repository.BatteryRepository;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import com.raihanorium.vpp.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;
    @Mock
    private BatteryRepositorySupport batteryRepositorySupport;
    @Mock
    private ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    @InjectMocks
    private BatteryServiceImpl batteryService;

    @Test
    @DisplayName("Should return battery names, total wattage, and average wattage for given postcodes")
    void testGetBatteries_withValidPostcodes_returnsCorrectResponse() {
        // Arrange
        List<String> postcodes = Arrays.asList("3010", "6107");
        GetBatteriesRequest request = GetBatteriesRequest.builder()
                .postcodes(postcodes)
                .build();
        GetBatteriesResponse returnValue = GetBatteriesResponse.builder()
                .batteryNames(Arrays.asList("Cannington", "University of Melbourne"))
                .totalWattage(98500L)
                .averageWattage(49250.0)
                .build();
        when(batteryRepositorySupport.findAll(request)).thenReturn(returnValue);

        // Act
        GetBatteriesResponse response = batteryService.getBatteries(request);

        // Assert
        assertEquals(Arrays.asList("Cannington", "University of Melbourne"), response.batteryNames(), "Battery names should be sorted alphabetically");
        assertEquals(98500, response.totalWattage(), "Total wattage should be sum of individual batteries");
        assertEquals(49250.0, response.averageWattage(), "Average wattage should be correct");
    }

    @Test
    @DisplayName("Should return empty response when no batteries found for given postcodes")
    void testGetBatteries_withNoMatchingPostcodes_returnsEmptyResponse() {
        // Arrange
        List<String> postcodes = Collections.singletonList("9999");
        GetBatteriesRequest request = GetBatteriesRequest.builder()
                .postcodes(postcodes)
                .build();
        when(batteryRepositorySupport.findAll(request)).thenReturn(GetBatteriesResponse.builder()
                .batteryNames(Collections.emptyList())
                .build());

        // Act
        GetBatteriesResponse response = batteryService.getBatteries(request);

        // Assert
        assertEquals(0, response.batteryNames().size(), "Battery names should be empty");
        assertEquals(0, response.totalWattage(), "Total wattage should be zero");
        assertEquals(0.0, response.averageWattage(), "Average wattage should be zero");
    }

    @Test
    void testSaveBatteries_returnsQueuedMessage() {
        // Arrange
        BatteryDto batteryDto1 = new BatteryDto(1L, "Battery A", "2000", 10000);
        BatteryDto batteryDto2 = new BatteryDto(2L, "Battery B", "3000", 20000);
        SaveBatteriesRequest request = new SaveBatteriesRequest(Arrays.asList(batteryDto1, batteryDto2));

        // Act
        SaveBatteriesResponse response = batteryService.saveBatteries(request);

        // Assert
        assertEquals("Batteries are being saved in the background", response.message());
    }

    @Test
    @DisplayName("Should save batteries and return queued message")
    void testSaveBatteries_withValidRequest_returnsQueuedMessage() {
        // Arrange
        BatteryDto batteryDto1 = new BatteryDto(1L, "Battery A", "2000", 10000);
        BatteryDto batteryDto2 = new BatteryDto(2L, "Battery B", "3000", 20000);
        SaveBatteriesRequest request = new SaveBatteriesRequest(List.of(batteryDto1, batteryDto2));

        // Act
        SaveBatteriesResponse response = batteryService.saveBatteries(request);

        // Assert
        assertEquals("Batteries are being saved in the background", response.message());
    }
}
