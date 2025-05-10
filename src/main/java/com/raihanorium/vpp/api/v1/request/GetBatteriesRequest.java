package com.raihanorium.vpp.api.v1.request;

import java.util.List;

public record GetBatteriesRequest(
        List<String> postcodes
) {
}
