package org.youyk.sec06.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RevenueService implements OrderProcessor  {

    //key : category , value : revenue
    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        Integer currentRevenue = db.getOrDefault(order.category(), 0);
        int updatedRevenue = currentRevenue + order.price();
        db.put(order.category(), updatedRevenue);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i-> this.db.toString());
    }
}
