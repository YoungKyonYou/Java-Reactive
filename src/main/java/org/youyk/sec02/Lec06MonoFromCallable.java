package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromCallable {
    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

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
        //아래 fromCallable과의 차이는
        //Supplier는 예외를 던질 수 없는 함수형 인터페이스입니다.
        // 따라서 sum(list) 메소드가 체크 예외를 던지는 경우,
        // Supplier 내에서 이를 처리해야 합니다. 그렇지 않으면 컴파일 오류가 발생합니다.
        // RuntimeException은 있으나 Checked Exception은 없다.
        Mono.fromSupplier(() -> sum(list))
                .subscribe(Util.subscriber());

        //Callable은 예외를 던질 수 있는 함수형 인터페이스입니다.
        // 따라서 sum(list) 메소드가 체크 예외를 던지는 경우, 이를 Callable 내에서 처리하지 않아도 됩니다.
        // 예외가 발생하면 Mono가 이를 감지하고 onError 시그널을 발생시킵니다.
        Mono.fromCallable(() -> sum(list))
                .subscribe(Util.subscriber());
        /**
         * 따라서, Mono.fromSupplier()와 Mono.fromCallable()
         * 사이에서 선택할 때는 연산이 예외를 던질 수 있는지 여부를 고려해야 합니다.
         */

    }

    private static int sum(List<Integer> list){
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
