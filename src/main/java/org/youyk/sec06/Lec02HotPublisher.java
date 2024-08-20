package org.youyk.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
/*
    Hot - 1 data producer for all the subscribers.
    share => publish().refCount(1)
    It needs 1 min subscriber to emit data.
    It stops when there is 0 subscriber.
    Re-subscription - It starts again where there is a new subscriber.
    To have min 2 subscribers, use publish().refCount(2);
 */
public class Lec02HotPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher.class);

    public static void main(String[] args) {
        // refCount의 의미는 최소 몇개의 subscriber가 있어야 데이터를 emit할 것인가를 의미한다.
        // data publish하려면 최소 1명의 subscriber가 필요한 것이다.
       /* Flux<String> movieFlux = movieStream().share().publish().refCount(2);*/

        Flux<String> movieFlux = movieStream().share();

        Util.sleepSeconds(2);

        movieFlux.take(1).subscribe(Util.subscriber("sam"));
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
