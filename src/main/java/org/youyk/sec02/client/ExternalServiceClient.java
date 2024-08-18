package org.youyk.sec02.client;

import org.youyk.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

/*
When the method is invoked, we create a Mono which is capable of sending a request. But the actual
HTTP request is sent, only when it is subscribed.
 */
public class ExternalServiceClient extends AbstractHttpClient {
    public Mono<String> getProductName(int productId){
        return this.httpClient.get()
                //request를 보내는 부분
                .uri("/demo01/product/"+productId)
                //response를 받는 부분
                .responseContent()
                .asString()
                .next();
    }
}
