package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.assignment.ExternalServiceClient;
import reactor.core.publisher.Flux;

/*
    Ensure that the external service is up and running!
 */
public class Lec13ConcatMap {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        /**
         * oncatMap의 주요 특징은 연산의 결과 스트림을 순차적으로 연결(concatenate)한다는 점입니다.
         * 즉, 한 연산의 결과 스트림이 완료될 때까지 다음 연산을 시작하지 않습니다.
         * 이는 flatMap과는 다르게, 결과 스트림의 순서를 원본 스트림의 순서와 동일하게 유지합니다.
         */
        Flux.range(1, 10)
                .concatMap(client::getProduct)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);

    }

}