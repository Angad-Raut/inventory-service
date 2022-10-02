package com.microservice.inventoryservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryDto {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
