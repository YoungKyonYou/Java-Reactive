package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec05MonoFromSupplier {
    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3);
        //이 코드는 sum(list) 메소드를 즉시 실행하고 그 결과를 Mono에 포장합니다.
        // 이는 Mono.just()가 호출되는 시점에 sum(list)가 실행되므로,
        // Mono가 구독되기 전에 이미 연산이 수행됩니다
        Mono.just(sum(list))
                .subscribe(Util.subscriber());

        // 이 코드는 sum(list) 메소드를 Supplier로 감싸 Mono에 전달합니다.
        // Mono.fromSupplier()는 구독이 발생할 때까지 Supplier의 실행을 지연합니다.
        // 따라서 Mono가 구독되는 시점에 sum(list)가 실행됩니다
        Mono.fromSupplier(() -> sum(list))
                .subscribe(Util.subscriber());
    }

    private static int sum(List<Integer> list){
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
