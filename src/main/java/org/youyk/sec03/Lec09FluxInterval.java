package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    public static void main(String[] args) {
        //sleepSeconds가 무한이면 계속 돌아가게 된다.
        Flux.interval(Duration.ofMillis(500))
                .map(i-> Util.faker().name().firstName())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
}
