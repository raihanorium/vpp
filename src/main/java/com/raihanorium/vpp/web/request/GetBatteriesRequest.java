package com.raihanorium.vpp.web.request;

import java.util.List;

public record GetBatteriesRequest(
        List<String> postcodes
) {
}
