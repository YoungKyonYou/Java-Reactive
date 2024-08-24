package org.youyk.sec10;

import java.time.Duration;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec03Window {

    public static void main(String[] args) {

        eventStream()
                /**
                 * window 연산자는 주어진 시간 동안 스트림에서 방출된 데이터를 하나의 Flux로 그룹화합니다.
                 * 이 경우, Duration.ofMillis(1800)는 1.8초 동안 스트림에서 방출된 데이터를 하나의 Flux로 그룹화합니다.
                 * 이렇게 생성된 각 Flux는 1.8초 동안 스트림에서 방출된 데이터를 포함하게 됩니다.
                 *
                 * Flux.window는 리액터(Reactor) 라이브러리에서 Flux 스트림을 일정한 크기의 하위 스트림(또는 하위 Flux)으로 분할하는 데 사용됩니다.
                 * 이 연산자는 데이터를 '윈도우(window)'로 나누어, 원본 Flux를 여러 작은 Flux로 그룹화하여 처리할 수 있게 합니다.
                 */
                .window(Duration.ofMillis(1800))
                .flatMap(Lec03Window::processEvents)
                .subscribe();


        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux.doOnNext(e -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }


}
