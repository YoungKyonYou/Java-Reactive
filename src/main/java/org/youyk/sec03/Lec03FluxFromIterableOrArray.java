package org.youyk.sec03;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {
    public static void main(String[] args) {

        List<String> list = List.of("a", "b", "c");
        //Flux.fromIterable(list)는 주어진 list의 각 요소를 비동기적으로 발행하는
        // Flux를 생성합니다.
        // Flux는 0개 이상의 데이터를 비동기적으로 발행할 수 있는 Publisher입니다.
        Flux.fromIterable(list)
                //subscribe(Util.subscriber())는 Flux가 발행하는 데이터를 구독하는 Subscriber를 등록합니다.
                // 이 Subscriber는 Util.subscriber()에 의해 생성됩니다.
                .subscribe(Util.subscriber());

        Integer[] arr = {1, 2, 3, 4,5,6};
        Flux.fromArray(arr)
                .subscribe(Util.subscriber());

    }
}
