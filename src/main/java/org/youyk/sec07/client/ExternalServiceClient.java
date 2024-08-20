package org.youyk.sec07.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.AbstractHttpClient;
import org.youyk.sec07.Lec03MultipleSubscriptionOn;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 이 코드는 HTTP 요청을 보내고 응답을 처리하는 과정을 비동기적으로 처리합니다. 이 과정에서 두 가지 주요 쓰레드가 사용됩니다: 이벤트 루프 쓰레드와 boundedElastic 쓰레드.
 * 이벤트 루프 쓰레드: 이 쓰레드는 네트워크 I/O 작업을 비동기적으로 처리합니다. 이 경우에는 HTTP 요청을 보내는 작업을 담당합니다. 이벤트 루프 쓰레드는 I/O 작업이 완료될 때까지 기다리지 않고 다른 작업을 계속 처리합니다. 이렇게 하면 시스템의 전반적인 처리량을 향상시킬 수 있습니다.
 * boundedElastic 쓰레드: 이 쓰레드는 네트워크 I/O 작업이 완료된 후에 데이터를 처리하는 작업을 담당합니다. 이 경우에는 HTTP 응답을 받아서 처리하는 작업을 담당합니다. boundedElastic 쓰레드는 I/O 블로킹 작업이나 긴 실행 시간을 가진 작업을 처리하는 데 적합합니다.
 * .publishOn(Schedulers.boundedElastic()) 코드는 이벤트 루프 쓰레드에서 boundedElastic 쓰레드로 작업을 전환하는 역할을 합니다. 이렇게 하면 이벤트 루프 쓰레드는 다음 I/O 작업을 빠르게 처리할 수 있고, boundedElastic 쓰레드는 데이터 처리 작업을 병렬로 처리할 수 있습니다. 이는 전반적인 시스템 성능을 향상시키는 데 도움이 됩니다.
 */
/*
When the method is invoked, we create a Mono which is capable of sending a request. But the actual
HTTP request is sent, only when it is subscribed.
 */
public class ExternalServiceClient extends AbstractHttpClient {
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);
    public Mono<String> getProductName(int productId){
        return this.httpClient.get()
                //request를 보내는 부분
                .uri("/demo01/product/"+productId)
                //response를 받는 부분
                .responseContent()
                .asString()
                .doOnNext(m -> log.info("next: {}", m))
                .next()
                //eventLoop thread는 io 작업을 실행하고 boundedElastic thread에서 received data를 처리한다.
                .publishOn(Schedulers.boundedElastic());
    }
}
