package oliver.tech.smartstock.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseRequest(@JsonProperty("item_id") String itemId,
                              @JsonProperty("item_name") String itemName,
                              @JsonProperty("supplier_name") String supplierName,
                              @JsonProperty("supplier_email") String supplieremail,
                              @JsonProperty("quantity") Integer quantity) {
}
