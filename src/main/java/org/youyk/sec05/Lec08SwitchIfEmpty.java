package org.youyk.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {
    private static final Logger log = LoggerFactory.getLogger(Lec08SwitchIfEmpty.class);

    public static void main(String[] args) {
        //usecase : redis + postgres가 있다고 해보자. productName를 캐시에 찾아본다 만약 empty면 database에서 찾는다. 이런 경우에 사용할 수 있다.
        Flux.range(1,10)
                .filter(i->i>10)
                //하나라도 값이 없으면 default값을 준다.
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback(){
        return Flux.range(100, 3);
    }
}
