package org.youyk.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public abstract class AbstractHttpClient {
    private static final String BASE_URL = "http://localhost:7070";
    protected  final HttpClient httpClient;

    public AbstractHttpClient() {
        //thread 1개 생성
        // 첫 번째 인자는 이벤트 루프 그룹의 이름, 두 번째 인자는 이벤트 루프의 수, 세 번째 인자는 데몬 스레드를 사용할지 여부를 결정
        LoopResources loopResources = LoopResources.create("vins", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);

    }

}
