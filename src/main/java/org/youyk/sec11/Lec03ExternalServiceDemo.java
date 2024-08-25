package org.youyk.sec11;


import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec11.client.ExternalServiceClient;
import org.youyk.sec11.client.ServerError;
import reactor.util.retry.Retry;

/*
    Ensure that the external service is up and running!
 */
public class Lec03ExternalServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);

    public static void main(String[] args) {

        retry();

        Util.sleepSeconds(60);

    }

    /*
        This is just a demo. Do NOT bombard the remote server indefinitely w/o any delay
     */
    private static void repeat() {
        ExternalServiceClient client = new ExternalServiceClient();
        client.getCountry()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

    /*
        This is just a demo. Do NOT bombard the remote server indefinitely w/o any delay
    */
    private static void retry() {
        ExternalServiceClient client = new ExternalServiceClient();
        client.getProductName(2)
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(ex -> ServerError.class.equals(ex.getClass()))
                .doBeforeRetry(rs -> log.info("retrying {}", rs.failure().getMessage()));
    }

}
