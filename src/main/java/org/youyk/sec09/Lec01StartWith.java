package org.youyk.sec09;

/*
    Calls multiple publishers in a specific order
 */

import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec01StartWith {
    private static final Logger log = LoggerFactory.getLogger(Lec01StartWith.class);

    public static void main(String[] args) {
        demo4();

        Util.sleepSeconds(3);
    }

    private static void demo1(){
        producer1()
                .startWith(-1, 0)
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        producer1()
                .startWith(List.of(-2,-1,0))
                .subscribe(Util.subscriber());
    }

    private static void demo3(){
        producer1()
                .startWith(producer2())
                .subscribe(Util.subscriber());
    }

    private static void demo4(){
        producer1()
                .startWith(0)
                .startWith(producer2())
                .startWith(49, 50) //밑에서부터 위로 올라온다 즉 49, 50 머저 실행되고 나서 producer2가 실행되는 것이다.
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1(){
        return Flux.just(1,2,3)
                .doOnSubscribe(s-> log.info("subscribed to producer1"))
                .delayElements(Duration.ofMillis(10));
    }
    private static Flux<Integer> producer2(){
        return Flux.just(51,52,53)
                .doOnSubscribe(s-> log.info("subscribed to producer2"))
                .delayElements(Duration.ofMillis(10));
    }
}
