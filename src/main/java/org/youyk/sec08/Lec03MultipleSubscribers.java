package org.youyk.sec08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribers.class);

    public static void main(String[] args) {
        //이걸 실행하면 generating 256까지 처음에 되는데 그 이유가 buffer size가 256이기 때문이다.
        //여기서는 16으로 줄여본다.
        // System.getProperty("reactor.bufferSize.small", "16");
        //여긴 parallel thread에 의해 실행
        Flux<Integer> producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                ).cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        //boundedElastic Thread에 의해 실행
        producer
                .limitRate(5) //5개씩만 처리한다. 5개씩만 produce 한다
                .publishOn(Schedulers.boundedElastic())
                .map(Lec03MultipleSubscribers::timeConsumingTask)
                .subscribe(Util.subscriber("sub1"));

        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(60);
    }
    private static int timeConsumingTask(int i){
        Util.sleepSeconds(1);
        return 1;
    }
}
