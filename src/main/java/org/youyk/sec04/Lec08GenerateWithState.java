package org.youyk.sec04;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {
    public static void main(String[] args) {
        /*AtomicInteger atomicInteger = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
            String country = Util.faker().country().username();
            synchronousSink.next(country);
            atomicInteger.incrementAndGet();
            if(atomicInteger.get() == 10 || country.equalsIgnoreCase("canada")){
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());

        atomicInteger.incrementAndGet();*/

        Flux.generate(
                ()->0,
                (counter, sink) ->{
                    String country = Util.faker().country().name();
                    sink.next(country);
                    counter++;
                    if(counter == 10 || country.equalsIgnoreCase("canada")){
                        sink.complete();
                    }
                    return counter;
                }
        )
                .subscribe(Util.subscriber());
    }
}
