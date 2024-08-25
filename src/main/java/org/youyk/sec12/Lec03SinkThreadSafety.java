package org.youyk.sec12;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

@Slf4j
public class Lec03SinkThreadSafety {
    public static void main(String[] args) {
        demo2();
    }
    private static void demo1(){
        //handle through which we would push items
        // onBackPressureBuffer - unbounded queue(크기 제한이 없는 큐라는 뜻)
        // unicast() 메소드는 이 Sink가 단일 구독자를 가질 수 있음을 나타냅니다.
        // 즉, 한 번에 하나의 구독자만이 이 Sink에 연결될 수 있습니다.
        Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        //not thread safe
        ArrayList<Object> list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            final int j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j);
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());

    }

    private static void demo2(){
        //handle through which we would push items
        // onBackPressureBuffer - unbounded queue(크기 제한이 없는 큐라는 뜻)
        // unicast() 메소드는 이 Sink가 단일 구독자를 가질 수 있음을 나타냅니다.
        // 즉, 한 번에 하나의 구독자만이 이 Sink에 연결될 수 있습니다.
        Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();


        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        //not thread safe
        ArrayList<Object> list = new ArrayList<>();
        flux.subscribe(list::add);

        /**
         * FAIL_NON_SERIALIZED 결과는 데이터 방출 시도가 실패했음을 나타내며, 이는 일반적으로
         * 다음 두 가지 상황 중 하나에서 발생합니다:
         *
         * 동시에 여러 스레드에서 emitNext 메소드를 호출하려고 했을 때. unicast 메소드로 생성된
         * Sinks.Many 인스턴스는 한 번에 하나의 스레드에서만 데이터를 방출할 수 있습니다.
         * 따라서 동시에 여러 스레드에서 데이터를 방출하려고 하면 FAIL_NON_SERIALIZED 결과가 반환됩니다.
         * 이미 종료 신호가 방출된 후에 데이터를 방출하려고 했을 때. 리액티브 스트림 규칙에 따라,
         * 종료 신호가 방출된 후에는 더 이상 데이터를 방출할 수 없습니다. 따라서 종료 신호가 방출된 후에 데이터를 방출하려고
         * 하면 FAIL_NON_SERIALIZED 결과가 반환됩니다.
         */
        for (int i = 0; i < 1000; i++) {
            final int j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, (signal, emitResult) ->
                        EmitResult.FAIL_NON_SERIALIZED.equals(emitResult));
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());

    }
}
