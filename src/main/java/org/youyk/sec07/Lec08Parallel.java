package org.youyk.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec08Parallel {
    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    /**
     * 병렬 스트림을 다시 단일 스트림으로 변환하는 이유는 여러 가지가 있습니다:
     * 연산 순서 보장: 병렬 스트림에서는 각 스트림이 독립적으로 동작하기 때문에, 연산의 순서를 보장하기 어렵습니다. 그러나 sequential()을 사용하여 병렬 스트림을 단일 스트림으로 변환하면, 이후의 연산들은 순차적으로 실행됩니다. 이를 통해 연산의 순서를 보장할 수 있습니다.
     * 단일 스트림 연산자 사용: 일부 연산자는 단일 스트림에서만 동작합니다. 예를 들어, reduce()나 collect()와 같은 연산자는 단일 스트림에서만 사용할 수 있습니다. 이러한 연산자를 사용하려면 병렬 스트림을 단일 스트림으로 변환해야 합니다.
     * 결과 집계: 병렬 처리를 통해 얻은 결과를 하나의 결과로 집계하려면, 병렬 스트림을 단일 스트림으로 변환해야 합니다. 이를 통해 최종 결과를 얻을 수 있습니다.
     */
    public static void main(String[] args) {
        Flux.range(1,10)
                //3 task를 병렬로 돌린다.
                .parallel(3)
                .runOn(Schedulers.parallel()) //cpu intensive task에 좋다
                .map(Lec08Parallel::process)
                //sequential() 연산자는 병렬로 처리되는 데이터 스트림을 다시 단일 스트림으로 병합하는 역할을 합니다.
                .sequential()
                .map(i->i+"a")
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

    private static int process(int i){
        log.info("time consuming task {}", i);
        Util.sleepSeconds(1);
        return i * 2;
    }
}
