package org.youyk.sec10.assigment.buffer;

import java.time.LocalTime;
import java.util.Map;

public record RevenueReport(
        LocalTime time,
        Map<String, Integer> revenue
) {
}
