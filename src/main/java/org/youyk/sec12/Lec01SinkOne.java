package org.youyk.sec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec11.Lec03ExternalServiceDemo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.One;

/**
 * "Sink를 사용하는 이유"
 * 비동기 작업의 결과를 수동으로 발행하고 관리: Sinks.One을 사용하면, 비동기 작업의 결과를 수동으로 제어할 수 있습니다. 작업이 완료된 시점에 수동으로 값을 발행하거나,
 * 오류 발생 시 이를 구독자에게 전달할 수 있습니다.
 *
 * 여러 구독자에 동일한 값 전달: 여러 구독자가 동일한 데이터를 구독해야 하는 상황에서 Sinks.One을 사용하면,
 * 단일 값을 발행하고 이를 모든 구독자에게 전달할 수 있습니다.
 *
 * 결과를 Mono로 노출하여 리액티브 스트림과 쉽게 연동: 비동기 작업의 결과를 Mono로 노출하면, 리액티브 프로그래밍에서 제공하는 다양한 연산자와 쉽게 결합할 수 있습니다.
 * 예를 들어, retry, timeout, onErrorResume 등의 연산자와 함께 사용할 수 있습니다.
 */
public class Lec01SinkOne {
    private static final Logger log = LoggerFactory.getLogger(Lec01SinkOne.class);
    public static void main(String[] args) {
        demo3();
    }

    private static void demo1(){
        //Sinks.One은 단 하나의 값을 발행합니다. 발행할 값이 정해지면, 그 값은 해당 Sink에 연결된 모든 구독자들에게 전달됩니다.
        //보통 비동기 작업에서 작업의 결과가 하나의 값이거나, 작업의 결과로 오류가 발생했을 때 그 결과나 오류를 구독자에게 전달하기 위해 사용됩니다.
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber());

        //sink.tryEmitValue("hi");
//        sink.tryEmitEmpty();
        sink.tryEmitError(new RuntimeException("oops"));
    }

    //we can have multiple subscribers

    /**
     * Sinks.One은 여러 구독자를 지원합니다. 즉, 하나의 발행자에서 여러 구독자가 값을 구독할 수 있습니다.
     * 이는 여러 비동기 작업에서 동일한 결과를 여러 컴포넌트가 필요로 할 때 유용합니다.
     */
    private static void demo2(){
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        sink.tryEmitValue("hi");//이렇게 순서가 뒤바꿔도 된다 it simply emits the value, but in case of issue it will not notify you
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));

    }

    private static void demo3(){
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();


        mono.subscribe(Util.subscriber("sam"));


        //여기선 value도 emit하고 handler를 제공함으로서 일종의 액션을 취하고 싶은 것이다.
        sink.emitValue("hi",((signalType, emitResult) -> {
            log.info(signalType.name());
            log.info(emitResult.name());
            return false;
        }));

        sink.emitValue("hello",((signalType, emitResult) -> {
            log.info("hello");
            log.info(signalType.name());
            log.info(emitResult.name());
            return false;
        }));

    }
}
