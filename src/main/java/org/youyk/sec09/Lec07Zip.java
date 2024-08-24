package org.youyk.sec09;

import java.time.Duration;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {

    record Car(String body, String engine, String tires){}

    public static void main(String[] args) {
        Flux.zip(getBody(), getEngine(), getTires())
                //.map(t -> t.getT1() + ":" + t.getT2() + ":" + t.getT3()) //zip 에 넘겨진 요소들을 각각 map이든 doOnNext로든 처리할 수 있다.
                .map(t -> new Car(t.getT1(), t.getT2(), t.getT3()))
                //zip 에서 만약 하나라도 빈값이 나오면 그냥 끝난다.
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Flux<String> getBody(){
        return Flux.range(1, 5)
                .map(i -> "body-"+ i)
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine(){
        return Flux.range(1, 3)
                .map(i -> "engine-"+ i)
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<String> getTires(){
        return Flux.range(1, 10)
                .map(i -> "tires-"+ i)
                .delayElements(Duration.ofMillis(75));
    }



}
