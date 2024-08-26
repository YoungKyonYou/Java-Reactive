package org.youyk.sec08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Lec04FluxCreate {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) {
        //producer processing speed is 20 itmes per second
        //consumer processing speed is one item per second
        Flux<Integer> producer = Flux.create(sink -> {
                    for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }
                    sink.complete();
                })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        //boundedElastic Thread에 의해 실행
        producer
//                .limitRate(1)//상황을 악화시킨다. 500개가 다 끝나야 consume하기 시작한다
                .publishOn(Schedulers.boundedElastic())
                .map(Lec04FluxCreate::timeConsumingTask)
                .subscribe(Util.subscriber("sub1"));

        Util.sleepSeconds(60);
    }
    private static int timeConsumingTask(int i){
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return 1;
    }
}
