package org.youyk.sec08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Reactor provides backpressure handling strategies
    - buffer
    - drop
    - latest
    - error
 */
public class Lec05BackPressureStrategies {
    private static final Logger log = LoggerFactory.getLogger(Lec05BackPressureStrategies.class);
    public static void main(String[] args) {
        //producer processing speed is 20 itmes per second
        //consumer processing speed is one item per second
        Flux<Integer> producer = Flux.create(sink -> {
                    for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }
                    sink.complete();
                    //모든 subscriber 마다 전략을 다르게 가져갈 수 있는데 여기 파라미터를 하나 더 넘기면 모든 subscriber에 적용된다.
                }/*, FluxSink.OverflowStrategy.LATEST)*/)
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        /**
         * onBackpressureBuffer() 전략은 Flux가 생성하는 데이터를 버퍼에 저장하고,
         * 구독자가 준비되면 버퍼에서 데이터를 가져가도록 합니다.
         * 이 방법은 데이터를 생성하는 속도가 데이터를 처리하는 속도보다 빠를 때 유용합니다.
         */
        /**
         * onBackpressureError()
         * 이 전략을 사용하면, Flux가 생성하는 데이터를 구독자가 충분히 빠르게 처리하지 못하는 경우
         * MissingBackpressureException이 발생합니다.
         * 이 예외는 구독자가 데이터를 충분히 빠르게 처리하지 못하여 발생하는 backpressure 상황을 나타냅니다.
         * 따라서, onBackpressureError() 전략은 backpressure 상황을 허용하지 않고,
         * 이러한 상황이 발생하면 즉시 에러를 발생시키는 방식으로 이를 처리합니다.
         * 전략은 backpressure 상황을 절대로 허용하면 안 되는 경우에 유용합니다. 하지만, 이 전략을 사용하면
         * backpressure 상황이 발생하면 즉시 에러가 발생하므로, 이를 적절히 처리할 수 있는 방법이 필요합니다.
         */
        /**
         * onBackpressureBuffer(10)은 버퍼의 크기를 10으로 제한합니다. 즉, Flux가 생성하는 데이터는 최대 10개까지만 버퍼에 저장됩니다.
         * 버퍼가 가득 차면, 새로운 데이터를 버퍼에 추가할 수
         * 없으므로 BufferOverflowException이 발생합니다.
         */
        /**
         * 이 전략을 사용하면, Flux가 생성하는 데이터를 구독자가 충분히 빠르게 처리하지 못하는 경우,
         * 추가적으로 생성되는 데이터는 버퍼에 저장되지 않고 바로 버려집니다.
         * 이는 메모리 부족 문제를 방지할 수 있지만, 데이터 손실이 발생할 수 있습니다.
         * 따라서, onBackpressureDrop() 전략은 데이터 손실이 허용되는 경우에 유용합니다.
         * 하지만, 이 전략을 사용하면 backpressure
         * 상황이 발생하면 즉시 데이터가 버려지므로, 이를 적절히 처리할 수 있는 방법이 필요합니다.
         */
        /**
         * onBackpressureLatest()는 Reactor 프로젝트에서 제공하는 backpressure 전략 중 하나입니다.
         * 이 전략은 데이터를 생성하는 속도가 데이터를 처리하는 속도보다 빠를 때,
         * 즉 backpressure 상황이 발생했을 때 가장 최근에 생성된 데이터만을 유지하고
         * 이전에 버퍼에 저장된 데이터는 버립니다.
         * 이 전략을 사용하면, Flux가 생성하는 데이터를 구독자가 충분히 빠르게 처리하지 못하는 경우,
         * 가장 최근에 생성된 데이터만을 유지하고 나머지는 버려집니다.
         * 이는 메모리 부족 문제를 방지할 수 있지만, 데이터 손실이 발생할 수 있습니다.
         */
        //boundedElastic Thread에 의해 실행
        producer
//                .onBackpressureBuffer()
             //   .onBackpressureError()
               // .onBackpressureBuffer(10)
               // .onBackpressureDrop()
                .onBackpressureLatest()
                .log()
                .limitRate(1)//상황을 악화시킨다. 500개가 다 끝나야 consume하기 시작한다
                .publishOn(Schedulers.boundedElastic())
                .map(Lec05BackPressureStrategies::timeConsumingTask)
                .subscribe(Util.subscriber("sub1"));

        Util.sleepSeconds(60);
    }
    private static int timeConsumingTask(int i){
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return 1;
    }

}
