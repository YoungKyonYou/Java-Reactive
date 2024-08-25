package org.youyk.sec12;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

@Slf4j
public class Lec06MulticastDirectAllOrNothing {

    public static void main(String[] args) {
        demo1();
    }
    private static void demo1() {

        // directAllOrNothing은 모든 subscriber가 데이터를 받을 수 있을 때만 데이터를 전달한다.
        Many<Object> sink = Sinks.many().multicast().directAllOrNothing(/*100*/);

        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        //subscriber에 지연을 줬음
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 1000; i++) {
            EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }
}
