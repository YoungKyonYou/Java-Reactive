package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    public static void main(String[] args) {
        //start from 1, end at 10
        Flux.range(1, 10)
                .subscribe(Util.subscriber());

        Flux.range(1,10)
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());
    }
}
