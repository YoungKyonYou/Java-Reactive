package org.youyk.sec10;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

public class Lec05GroupedFlux {
    private static final Logger log = LoggerFactory.getLogger(Lec05GroupedFlux.class);

    public static void main(String[] args) {

        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
               // .map(i->i*2).startWith(1)
                .groupBy(i -> i % 2) // 0, 1
                .flatMap(Lec05GroupedFlux::processEvents)
                .subscribe();

        Util.sleepSeconds(60);

    }

    private static Mono<Void> processEvents(GroupedFlux<Integer, Integer> groupedFlux) {
        log.info("received flux for {}", groupedFlux.key());
        return groupedFlux.doOnNext(i -> log.info("key: {}, item: {}", groupedFlux.key(), i))
                //이렇게 테스트하는 이유는 비록 홀수가 먼저 방출되지만 다른 짝수가 다 실행되기 전까진 둘다 completed가 안되기 때문이다. 그래서 주의하자
              //  .doOnComplete(() -> log.info("{} completed", groupedFlux.key()))
                .then();
    }
}
