package org.youyk.sec05;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

/*
    Handle behaves like filter + map

    1 => -2
    4 => do not send
    7 => error
    everything else => send as it is
*/
public class Lec01Handle {
    public static void main(String[] args) {
       /* Flux<Integer> flux = Flux.range(1, 10);
        Flux<Object> flux1 = flux.handle((item, sink) -> {
            sink.error(new RuntimeException("oops"));
        });

        flux1.subscribe(Util.subscriber());*/
        Flux.range(1,10)
                .filter(i->i!=7)
                .handle((item, sink) -> {
                    switch(item){
                        case 1 -> sink.next(-2);
                        case 4 -> {}
                        case 7 -> sink.error(new RuntimeException("oops"));
                        default -> sink.next(item);
                    }
                })
                .cast(Integer.class)
                .subscribe(Util.subscriber());
    }
}
