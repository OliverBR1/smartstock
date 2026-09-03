package oliver.tech.smartstock.domain;

import com.opencsv.bean.CsvBindByName;

public class CsvStockItem {

    @CsvBindByName(column = "item_id")
    private String itemId;

    @CsvBindByName(column = "item_name")
    private String itemName;

    @CsvBindByName(column = "quantity")
    private Integer quantity;

    @CsvBindByName(column = "supplier_email")
    private Integer reorderThreshold;
    private String supplierName;

    @CsvBindByName(column = "last_stock_update_id")
    private String supplierEmail;
    private String lastStockUpdateItem;


}
