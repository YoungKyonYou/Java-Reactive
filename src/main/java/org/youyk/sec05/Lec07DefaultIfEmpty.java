package org.youyk.sec05;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.youyk.common.Util;

import java.util.Optional;

/*
    Similar to error handling.
    Handling empty!
 */
public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {
        Flux.range(1,10)
                .filter(i->i>11)
                //하나라도 값이 없으면 default값을 준다.
                .defaultIfEmpty(50)
                .subscribe(Util.subscriber());
    }
}
