package org.youyk.sec03.client;

import org.youyk.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
When the method is invoked, we create a Mono which is capable of sending a request. But the actual
HTTP request is sent, only when it is subscribed.
 */
public class ExternalServiceClient extends AbstractHttpClient {
    public Flux<String> getNames(){
        return this.httpClient.get()
                //request를 보내는 부분
                .uri("/demo02/username/stream")
                //response를 받는 부분
                .responseContent()
                .asString();
    }

    public Flux<Integer> getPriceChanges(){
        return this.httpClient.get()
                //request를 보내는 부분
                .uri("/demo02/stock/stream")
                //response를 받는 부분
                .responseContent()
                .asString()
                .map(Integer::parseInt);
    }
}
