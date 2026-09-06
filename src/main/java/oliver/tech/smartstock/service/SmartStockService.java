package oliver.tech.smartstock.service;

import oliver.tech.smartstock.client.dto.PurchaseRequest;
import oliver.tech.smartstock.domain.CsvStockItem;
import oliver.tech.smartstock.entity.PurchaseRequestEntity;
import oliver.tech.smartstock.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;
    private final PurchaseRequestRepository repository;

    public SmartStockService(ReportService reportService,
                             PurchaseSectorService purchaseSectorService, PurchaseRequestRepository repository) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
        this.repository = repository;
    }

    public void start(String reportPath){

        try{
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {

                if(item.getQuantity() < item.getReorderThreshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);

                   var purchasedWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                    persist(item, reorderQuantity, purchasedWithSuccess);
                }
            });
        }   catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(CsvStockItem item,
                         Integer reorderQuantity,
                         boolean purchasedWithSuccess) {

        var entity = new PurchaseRequestEntity();
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setQuantityOnStock(item.getQuantity());
        entity.setReorderThreshold(item.getReorderThreshold());
        entity.setSupplierName(item.getSupplierName());
        entity.setSupplierEmail(item.getSupplierEmail());
        entity.setLastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateItem()));

        entity.setPurchaseQuantity(reorderQuantity);
        entity.setPurchasedWithSuccess(purchasedWithSuccess);
        entity.setPurchaseDateTime(LocalDateTime.now());

        repository.save(entity);

    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }
}
