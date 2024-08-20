package org.youyk.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec02SubscribeOn.class);

    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("generating: {}", i);
                sink.next(i);
            }
            sink.complete();
        }).doOnNext(v -> log.info("value: {}", v))
                .doFirst(() -> log.info("first1"))
                .subscribeOn(Schedulers.boundedElastic())
                //여기 first2까지는 Main Thread로 찍힌다. 그 위로는 boundedElastic Thread로 찍힌다.
                .doFirst(() -> log.info("first2"));

        Runnable runnable1 = () -> flux
/*                .doFirst(() -> log.info("first1"))
                .subscribeOn(Schedulers.boundedElastic())
                //여기 first2까지는 Main Thread로 찍힌다. 그 위로는 boundedElastic Thread로 찍힌다.
                .doFirst(() -> log.info("first2"))*/
                .subscribe(Util.subscriber("sub1"));
        Runnable runnable2 = () -> flux
                .subscribe(Util.subscriber("sub2"));

        new Thread(runnable1).start(); //first2 까지만 별도 쓰레드로 돌고 그 위로는 다시 boundedElastic Thread로 돌아간다.
        new Thread(runnable2).start(); //first2 까지만 별도 쓰레드로 돌고 그 위로는 다시 boundedElastic Thread로 돌아간다.

        Util.sleepSeconds(2);
    }
}
