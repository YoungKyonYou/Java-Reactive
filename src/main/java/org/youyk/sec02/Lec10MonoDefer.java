package org.youyk.sec02;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

public class Lec10MonoDefer {
    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);
    public static void main(String[] args) {

        /**
         * Mono.defer를 사용하는 주요 이유는 연산의 지연 실행을 가능하게 하기 위해서입니다.
         * Mono.defer는 제공된 Supplier가 구독될 때까지 실행을 지연시킵니다.
         * 이는 연산이 필요할 때만 실행되도록 하여 리소스를 절약하는 데 도움이 됩니다.
         */
        //Mono.defer 를 사용해서 publisher의 지연을 구현할 수 있다.
        //publisher의 지연은 createPublisher() 메소드만 호출해보면 된다(subscribe 주석처리)
        //createPublisher();
        //Mono.defer(Lec10MonoDefer::createPublisher); 이렇게만 실행하면 아무것도 만들어지지 않는다.
        //subscribe를 붙이면 publisher 가 만들어지고 나서야 subscribe가 동작한다
        Mono.defer(Lec10MonoDefer::createPublisher)
            .subscribe(Util.subscriber());
    }

    private static Mono<Integer> createPublisher(){
        log.info("creating publisher");
        List<Integer> list = List.of(1, 2, 3);
        Util.sleepSeconds(1);
        return Mono.fromSupplier(() -> sum(list));
    }

    //time-consuming business logic
    private static int sum(List<Integer> list){
        log.info("finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(a -> a).sum();
    }
}
