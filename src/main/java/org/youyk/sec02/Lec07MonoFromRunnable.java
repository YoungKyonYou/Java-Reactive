package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {
    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {
        getProductName(2)
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getProductName(int productId){
        if(productId == 1){
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }

        // 주어진 Runnable을 실행하고, 그 결과를 Mono<Void>로 반환합니다.
        //이 메소드는 Runnable의 실행을 구독 시점까지 지연시키며, Runnable이 완료되면 onComplete 시그널을 발생시킵니다.
        // Mono.fromRunnable()은 주로 부작용(side-effect)을 수행하는 작업에 사용됩니다.
        // 예를 들어, 로깅, 통계 업데이트,
        // 데이터베이스의 상태 변경 등과 같은 작업을 Mono.fromRunnable()로 감쌀 수 있습니다.
        return Mono.fromRunnable(()-> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId){
        log.info("notifying business on unavailable product {}", productId);
    }
}
