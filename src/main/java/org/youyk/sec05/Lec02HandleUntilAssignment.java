package org.youyk.sec05;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {
    public static void main(String[] args) {
        Flux.<String>generate(sink -> sink.next(Util.faker().country().name()))
                .handle((item, sink) -> {
                    sink.next(item);
                    //handle 메소드는 아이템을 처리하고 다음 스트림으로 전달하는 로직을 포함하므로, sink.next(item) 이후의 코드도 계속 실행됩니다.
                    if(item.equalsIgnoreCase("canada")){
                        sink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }
}
