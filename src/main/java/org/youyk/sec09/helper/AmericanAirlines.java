package org.youyk.sec09.helper;

import java.time.Duration;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class AmericanAirlines {
    private static final String AIRLINE = "American Airlines";

    public static Flux<Flight> getFlights(){
        return Flux.range(1, Util.faker().random().nextInt(5,10))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(200,1200)))
                .map(i-> new Flight(AIRLINE, Util.faker().random().nextInt(300, 900)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
