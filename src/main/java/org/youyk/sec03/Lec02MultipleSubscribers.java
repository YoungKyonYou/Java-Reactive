package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux.subscribe(Util.subscriber("sub1"));
        flux.filter(i->i>7)
                .subscribe(Util.subscriber("sub2"));
        flux
                .filter(i-> i % 2 == 0) //stream 처럼 이렇게| 사용 가능
                .map(i->i+"a")
                .subscribe(Util.subscriber("sub3"));
    }
}
