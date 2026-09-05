package oliver.tech.smartstock.service;

import oliver.tech.smartstock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;

    public SmartStockService(ReportService reportService,
                             PurchaseSectorService purchaseSectorService) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
    }

    public void start(String reportPath){

        try{
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {

                if(item.getQuantity() < item.getReorderThreshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);

                    purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);
                }
            });
        }   catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }
}
