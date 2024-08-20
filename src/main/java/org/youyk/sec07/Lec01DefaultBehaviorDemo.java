package org.youyk.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec05.Lec05Subscribe;
import reactor.core.publisher.Flux;

public class Lec01DefaultBehaviorDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviorDemo.class);

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("generating: {}", i);
                sink.next(i);
            }
            sink.complete();
        }).doOnNext(v -> log.info("value: {}", v));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("sub1"));

        new Thread(runnable).start();
/*
        //이렇게 하면 두개 다 메인쓰레드에 의해서 실행된 것이다.
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));*/
    }
}
