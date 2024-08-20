package org.youyk.sec06.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor  {

    //key : category , value : revenue
    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        Integer currentInventory = db.getOrDefault(order.category(), 500);
        int updatedInventory = currentInventory - order.price();
        db.put(order.category(), updatedInventory);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i-> this.db.toString());
    }
}
