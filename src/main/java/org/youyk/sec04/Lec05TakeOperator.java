package org.youyk.sec04;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

/**
 * Take is similar to java stream's limit
 */
public class Lec05TakeOperator {
    public static void main(String[] args) {
/*        //take is similar to limit in java stream
        IntStream.rangeClosed(1, 10)
                .limit(3)
                .forEach(System.out::println);*/

  /*      Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());*/
        takeUntil();
    }

    //여기서는 첫 3개만 받는다
    private static void take(){
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());
    }
    //여기서는 숫자를 제공하기보다는 조건을 제공한다
    private static void takeWhile(){
        Flux.range(1, 10)
                .log("take")
                .takeWhile(i -> i <5) // stop when the condition is not met
                .log("sub")
                .subscribe(Util.subscriber());
    }

    //조건이 충족된 마지막 항목까지 포함하여 downstream으로 전달 즉 1까지만 전달 왜냐면 조건이 충족됐으니까
    private static void takeUntil(){
        Flux.range(1, 10)
                .log("take")
                .takeUntil(i -> i <5) // stop when the condition is met + allow the last item
                .log("sub")
                .subscribe(Util.subscriber());
    }
}
