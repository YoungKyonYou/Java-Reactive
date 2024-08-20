package org.youyk.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;


public class Lec04HotPublisherCache {
    private static final Logger log = LoggerFactory.getLogger(Lec04HotPublisherCache.class);

    public static void main(String[] args) {
        // refCount의 의미는 최소 몇개의 subscriber가 있어야 데이터를 emit할 것인가를 의미한다.
        // data publish하려면 최소 1명의 subscriber가 필요한 것이다.
       /* Flux<String> movieFlux = movieStream().share().publish().refCount(2);*/


        //replay(1)은 최근 1개의 데이터를 캐시하고 있다가 새로운 subscriber가 들어오면 캐시된 데이터를 전달한다.
        Flux<Integer> stockFlux = stockStream().replay(1).autoConnect(0);

        log.info("sam joining");
        Util.sleepSeconds(4);

        stockFlux.subscribe(Util.subscriber("sam"));
        Util.sleepSeconds(4);

        log.info("mike joining");
        stockFlux.subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    //movie theater
    private static Flux<Integer> stockStream(){
        //여기서 sink는 SynchronousSink 객체이다.
        return Flux.generate(sink -> sink.next(Util.faker().random().nextInt(10,100)))
                .delayElements(Duration.ofSeconds(3))
                .doOnNext(price -> log.info("emitting price {}", price))
                .cast(Integer.class);

    }
}
