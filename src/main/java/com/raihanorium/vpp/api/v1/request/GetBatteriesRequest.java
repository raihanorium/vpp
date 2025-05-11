package com.raihanorium.vpp.api.v1.request;

import com.raihanorium.vpp.api.v1.validator.PostcodeRequestRange;
import lombok.Builder;

import java.util.List;


@Builder
public record GetBatteriesRequest(
        @PostcodeRequestRange
        List<String> postcodes,
        Integer minimumCapacity,
        Integer maximumCapacity
) {
}
