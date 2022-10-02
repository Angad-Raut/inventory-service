package com.microservice.inventoryservice.serviceImpl;

import com.microservice.inventoryservice.dto.EntityIdDto;
import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.dto.SkuCodeDto;
import com.microservice.inventoryservice.exception.AlreadyExistException;
import com.microservice.inventoryservice.exception.InvalidDateException;
import com.microservice.inventoryservice.exception.ResourceNotFoundException;
import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import com.microservice.inventoryservice.service.InventoryService;
import com.microservice.inventoryservice.util.Constants;
import com.microservice.inventoryservice.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ErrorCode errorCode;

    @Override
    public Boolean isItemExist(SkuCodeDto skuCode) {
        if (inventoryRepository.getSkuCode(skuCode.getSkuCode())!=null) {
            return Constants.ACTIVE_STATUS;
        } else {
            return Constants.DIACTIVE_STATUS;
        }
    }

    @Override
    public String addInventoryDetails(InventoryDto requestDto) throws InvalidDateException, AlreadyExistException {
        Inventory inventory = null;
        String message=null;
        try{
            SkuCodeDto skuCodeDto = SkuCodeDto.builder().skuCode(requestDto.getSkuCode()).build();
            if(requestDto.getId()==null) {
                if(isItemExist(skuCodeDto)) {
                    throw new AlreadyExistException(errorCode.INVENTORY_RECORD_ALREADY_PRESENT);
                }
                inventory = Inventory.builder()
                        .skuCode(requestDto.getSkuCode())
                        .quantity(requestDto.getQuantity())
                        .status(true)
                        .insertTime(new Date())
                        .updateTime(new Date())
                        .build();
                message = Constants.RECORD_ADDED;
            }else {
                Inventory inventory1 = inventoryRepository.getById(requestDto.getId());
                if(!requestDto.getSkuCode().equals(inventory1.getSkuCode())) {
                    if(isItemExist(skuCodeDto)) {
                        throw new AlreadyExistException(errorCode.INVENTORY_RECORD_ALREADY_PRESENT);
                    }
                    inventory.setSkuCode(requestDto.getSkuCode());
                }
                if(!requestDto.getQuantity().equals(inventory1.getQuantity())) {
                    inventory.setQuantity(requestDto.getQuantity());
                }
                inventory.setUpdateTime(new Date());
                message = Constants.RECORD_UPDATED;
            }
            inventoryRepository.save(inventory);
            return message;
        }catch(DataIntegrityViolationException e) {
            throw new InvalidDateException(errorCode.DATABASE_CONSTRAINT_VIOLATION_ISSUE_OCCURRED);
        }
    }

    @Override
    public Inventory getById(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Inventory inventory = inventoryRepository.getById(entityIdDto.getEntityId());
        if(inventory==null) {
            throw new ResourceNotFoundException(errorCode.INVENTORY_RECORD_NOT_FOUND);
        }
        return inventory;
    }

    @Override
    public List<InventoryDto> getAllInventory() {
        List<InventoryDto> list = new ArrayList<>();
        for (Inventory inventory : inventoryRepository.findAll()) {
            list.add(InventoryDto.builder()
                    .id(inventory.getId())
                    .skuCode(inventory.getSkuCode())
                    .quantity(inventory.getQuantity())
                    .build());
        }
        return list;
    }

    @Override
    public String updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException {
        Inventory inventory = inventoryRepository.getById(entityIdDto.getEntityId());
        if(inventory!=null) {
             if (inventory.getStatus()) {
                  inventoryRepository.updateStatus(entityIdDto.getEntityId(),Constants.DIACTIVE_STATUS);
                  return Constants.STATUS_DISABLED;
             } else {
                  inventoryRepository.updateStatus(entityIdDto.getEntityId(), Constants.ACTIVE_STATUS);
                  return Constants.STATUS_ENABLED;
             }
        }
        throw new ResourceNotFoundException(errorCode.INVENTORY_RECORD_NOT_FOUND);
    }
}
