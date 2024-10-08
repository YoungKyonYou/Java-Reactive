package org.youyk.sec04;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {
    public static void main(String[] args) {
        demo2();
    }

    private static void demo1(){
        Flux.generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
            if(country.equalsIgnoreCase("canada")){
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }

    private static void demo2(){
        Flux.<String>generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
        })
                .takeUntil(c -> c.equalsIgnoreCase("canada")).subscribe(Util.subscriber());
    }
}
