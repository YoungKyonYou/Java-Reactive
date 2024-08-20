package org.youyk.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    almost same as publish().refCount(1).
    - does NOT stop when subscribers cancel. So it will start producing even for 0 subscribers once it started.
    - make it real hot - publish().autoConnect(0)
 */
public class Lec03HotPublisherAutoConnect {
    private static final Logger log = LoggerFactory.getLogger(Lec03HotPublisherAutoConnect.class);

    public static void main(String[] args) {
        // refCount의 의미는 최소 몇개의 subscriber가 있어야 데이터를 emit할 것인가를 의미한다.
        // data publish하려면 최소 1명의 subscriber가 필요한 것이다.
       /* Flux<String> movieFlux = movieStream().share().publish().refCount(2);*/


        //autoConnect(0)은 Subscriber의 수와 상관없이 즉시 데이터를 emit하기 시작합니다.
        Flux<String> movieFlux = movieStream().publish().autoConnect(0);

        Util.sleepSeconds(2);

        movieFlux.take(4).subscribe(Util.subscriber("sam"));
        Util.sleepSeconds(3);
        movieFlux.subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    //movie theater
    private static Flux<String> movieStream(){
        return Flux.generate(
                () -> {
                    log.info("received the request");
                    return 1;
                },
                (state, sink) ->{
                    String scene = "movie scene " + state;
                    log.info("playing {}", scene);
                    sink.next(scene);
                    return ++state;
                }
        )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
