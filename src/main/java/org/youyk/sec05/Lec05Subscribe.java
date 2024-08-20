package org.youyk.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05Subscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);

    public static void main(String[] args) {
        Flux.range(1,10)
                .doOnNext( i ->log.info("received: {}", i))
                .doOnComplete(()->log.info("complete"))
                .doOnError(err -> log.info("error", err))
                .subscribe();
    }
}
