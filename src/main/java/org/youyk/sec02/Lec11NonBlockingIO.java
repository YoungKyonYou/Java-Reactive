package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec02.client.ExternalServiceClient;

/*
    To demo non-blocking IO
    Ensure that the external service is up and running
 */
public class Lec11NonBlockingIO {
    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        log.info("starting");

        for (int i = 1; i <=5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }


        //이거 없이 실행하면 메인쓰레드가 바로 끝내버린다. 그래서 기다려주기 위해 사용
        Util.sleepSeconds(2);
    }
}
