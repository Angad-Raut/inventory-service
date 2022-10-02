package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.dto.EntityIdDto;
import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.dto.SkuCodeDto;
import com.microservice.inventoryservice.exception.AlreadyExistException;
import com.microservice.inventoryservice.exception.InvalidDateException;
import com.microservice.inventoryservice.exception.ResourceNotFoundException;
import com.microservice.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {
    Boolean isItemExist(SkuCodeDto skuCodeDto);
    String addInventoryDetails(InventoryDto requestDto)throws InvalidDateException, AlreadyExistException;
    Inventory getById(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    List<InventoryDto> getAllInventory();
    String updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException;
}
