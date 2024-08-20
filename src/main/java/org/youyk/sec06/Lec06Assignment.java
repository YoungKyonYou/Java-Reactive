package org.youyk.sec06;

import org.youyk.common.Util;
import org.youyk.sec06.assignment.ExternalServiceClient;
import org.youyk.sec06.assignment.InventoryService;
import org.youyk.sec06.assignment.RevenueService;

public class Lec06Assignment {
    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();

        client.orderStream().subscribe(inventoryService::consume);
        client.orderStream().subscribe(revenueService::consume);

        inventoryService.stream()
                .subscribe(Util.subscriber("inventory"));
        revenueService.stream()
                .subscribe(Util.subscriber("revenue"));

        client.orderStream().subscribe(inventoryService::consume);
        client.orderStream().subscribe(revenueService::consume);
    }
}
