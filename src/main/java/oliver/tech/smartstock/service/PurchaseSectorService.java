package oliver.tech.smartstock.service;

import oliver.tech.smartstock.client.PurchaseSectorClient;
import oliver.tech.smartstock.client.dto.PurchaseRequest;
import oliver.tech.smartstock.domain.CsvStockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSectorService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseSectorService.class);

    private final AuthService authService;
    private final PurchaseSectorClient purchaseSectorClient;

    public PurchaseSectorService(AuthService authService, PurchaseSectorClient purchaseSectorClient) {
        this.authService = authService;
        this.purchaseSectorClient = purchaseSectorClient;
    }

    public boolean sendPurchaseRequest(CsvStockItem item, Integer purchaseQuantity) {

        var token = authService.getToken();

        var request = new PurchaseRequest(
                item.getItemId(),
                item.getItemName(),
                item.getSupplierName(),
                item.getSupplierEmail(),
                purchaseQuantity
        );

        var response = purchaseSectorClient.sendPurchaseRequest(token,request);

        if(response.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            logger.error("error while sending purchase request, " +
                            "status: {}, response: {}",
                    response.getStatusCode(), response.getBody());
            return false;
        }

        return true;
    }
}
