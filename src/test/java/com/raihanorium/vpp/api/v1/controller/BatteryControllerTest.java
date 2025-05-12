package com.raihanorium.vpp.api.v1.controller;

import com.raihanorium.vpp.api.v1.dto.BatteryDto;
import com.raihanorium.vpp.api.v1.request.GetBatteriesRequest;
import com.raihanorium.vpp.api.v1.request.SaveBatteriesRequest;
import com.raihanorium.vpp.api.v1.response.GetBatteriesResponse;
import com.raihanorium.vpp.config.TestcontainersConfiguration;
import com.raihanorium.vpp.service.BatteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class BatteryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BatteryService batteryService;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        batteryService.deleteAllBatteries();
        // Add test data
        batteryService.saveBatteries(
                new SaveBatteriesRequest(
                        List.of(
                                BatteryDto.builder().name("Test3").postcode("55644").capacity(5).build(),
                                BatteryDto.builder().name("Test2").postcode("3010").capacity(50000).build(),
                                BatteryDto.builder().name("Test1").postcode("6107").capacity(48500).build(),
                                BatteryDto.builder().name("Test4").postcode("6107").capacity(90500).build()
                        )));
    }

    @Test
    void testGetBatteries() {
        HttpEntity<GetBatteriesRequest> payload = new HttpEntity<>(GetBatteriesRequest.builder()
                .postcodes(List.of("3010", "6107"))
                .minimumCapacity(10)
                .maximumCapacity(50000)
                .build());
        GetBatteriesResponse response = restTemplate.exchange("/api/v1/batteries", HttpMethod.GET, payload, GetBatteriesResponse.class).getBody();
        assertNotNull(response, "Response should not be null");
        assertEquals(2, response.batteryNames().size(), "Battery names size should be 2");
        assertEquals("Test1", response.batteryNames().get(0), "Battery names should be sorted alphabetically");
        assertEquals((50000 + 48500), response.totalWattage(), "Total wattage should be sum of individual batteries");
        assertEquals((50000 + 48500) / 2.0, response.averageWattage(), "Average wattage should be correct");
    }

    @Test
    void testSaveBatteries() {
        List<BatteryDto> batteries = List.of(
                BatteryDto.builder().name("Test5").postcode("3010").capacity(50000).build(),
                BatteryDto.builder().name("Test6").postcode("6107").capacity(48500).build()
        );
        SaveBatteriesRequest request = new SaveBatteriesRequest(batteries);
        List<BatteryDto> response = restTemplate.postForObject("/api/v1/batteries", request, List.class);
        assertNotNull(response, "Response should not be null");
        assertEquals(2, response.size(), "Response size should be 2");
    }
}
