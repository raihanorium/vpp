package com.raihanorium.vpp.service;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.persistence.Battery;
import com.raihanorium.vpp.repository.BatteryRepository;
import com.raihanorium.vpp.repository.BatteryRepositorySupport;
import com.raihanorium.vpp.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;
    @Mock
    private BatteryRepositorySupport batteryRepositorySupport;

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
    void testSaveBatteries_returnsSavedDtos() {
        // Arrange
        BatteryDto batteryDto1 = new BatteryDto(1L, "Battery A", "2000", 10000);
        BatteryDto batteryDto2 = new BatteryDto(2L, "Battery B", "3000", 20000);
        SaveBatteriesRequest request = new SaveBatteriesRequest(Arrays.asList(batteryDto1, batteryDto2));

        Battery battery1 = BatteryDto.toEntity(batteryDto1);
        Battery battery2 = BatteryDto.toEntity(batteryDto2);
        List<Battery> savedEntities = Arrays.asList(battery1, battery2);

        when(batteryRepository.saveAll(anyList())).thenReturn(savedEntities);

        // Act
        List<BatteryDto> resultDtos = batteryService.saveBatteries(request);

        // Assert
        assertEquals(2, resultDtos.size());
        assertEquals("Battery A", resultDtos.get(0).name());
        assertEquals("Battery B", resultDtos.get(1).name());
    }

    @Test
    @DisplayName("Should save batteries and return saved BatteryDtos")
    void testSaveBatteries_withValidRequest_returnsSavedDtos() {
        // Arrange
        BatteryDto batteryDto1 = new BatteryDto(1L, "Battery A", "2000", 10000);
        BatteryDto batteryDto2 = new BatteryDto(2L, "Battery B", "3000", 20000);
        SaveBatteriesRequest request = new SaveBatteriesRequest(List.of(batteryDto1, batteryDto2));

        Battery battery1 = BatteryDto.toEntity(batteryDto1);
        Battery battery2 = BatteryDto.toEntity(batteryDto2);
        List<Battery> savedEntities = List.of(battery1, battery2);

        when(batteryRepository.saveAll(anyList())).thenReturn(savedEntities);

        // Act
        List<BatteryDto> resultDtos = batteryService.saveBatteries(request);

        // Assert
        assertEquals(2, resultDtos.size());
        assertEquals("Battery A", resultDtos.get(0).name());
        assertEquals("Battery B", resultDtos.get(1).name());
    }

    @Test
    @DisplayName("Should return empty list when no batteries are provided")
    void testSaveBatteries_withEmptyRequest_returnsEmptyList() {
        // Arrange
        SaveBatteriesRequest request = new SaveBatteriesRequest(Collections.emptyList());

        when(batteryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        List<BatteryDto> resultDtos = batteryService.saveBatteries(request);

        // Assert
        assertEquals(0, resultDtos.size());
    }
}
