package org.youyk.sec09.helper;

import java.time.Duration;
import reactor.core.publisher.Flux;

public class Kayak {
    public static Flux<Flight> getFlights(){
        return Flux.merge(
                AmericanAirlines.getFlights(),
                Emirates.getFlights(),
                Qatar.getFlights()
        )
                .take(Duration.ofSeconds(2)); //스트림에서 2초 동안 데이터를 수신하도록 설정하고 있습니다. 2초가 지나면 스트림은 자동으로 종료됩니다.
    }
}
