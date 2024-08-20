package org.youyk.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec07.client.ExternalServiceClient;

public class Lec06EventLoopIssueFix {
    private static final Logger log = LoggerFactory.getLogger(Lec06EventLoopIssueFix.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        log.info("starting");
        for (int i = 1; i <=5; i++) {
            //publisher임
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
            .subscribe(Util.subscriber());
        }


        //이거 없이 실행하면 메인쓰레드가 바로 끝내버린다. 그래서 기다려주기 위해 사용
        Util.sleepSeconds(20);
    }

    private static String process(String input){
        Util.sleepSeconds(1);
        return input+"-processed";
    }
}
