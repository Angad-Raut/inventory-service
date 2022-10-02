package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.EntityIdDto;
import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.dto.ResponseDto;
import com.microservice.inventoryservice.dto.SkuCodeDto;
import com.microservice.inventoryservice.exception.AlreadyExistException;
import com.microservice.inventoryservice.exception.InvalidDateException;
import com.microservice.inventoryservice.exception.ResourceNotFoundException;
import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.service.InventoryService;
import com.microservice.inventoryservice.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PostMapping
    @RequestMapping(value = "/addRecord")
    public ResponseEntity<ResponseDto<String>> addDetails(@RequestBody InventoryDto inventoryDto, BindingResult result) {
          if(result.hasErrors()) {
              return errorHandler.handleValidationErrors(result);
          }
          try{
              return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(
                      inventoryService.addInventoryDetails(inventoryDto),null),HttpStatus.CREATED);
          }catch(InvalidDateException | AlreadyExistException e) {
              return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(null,e.getMessage()), HttpStatus.OK);
          }
    }

    @PostMapping
    @RequestMapping(value = "/getById")
    public ResponseEntity<ResponseDto<Inventory>> getById(@RequestBody EntityIdDto entityIdDto, BindingResult result) {
        if(result.hasErrors()) {
            return errorHandler.handleValidationErrors(result);
        }
        try{
            return new ResponseEntity<ResponseDto<Inventory>>(new ResponseDto<Inventory>(
                    inventoryService.getById(entityIdDto),null),HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            return new ResponseEntity<ResponseDto<Inventory>>(new ResponseDto<Inventory>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping
    @RequestMapping(value = "/updateStatus")
    public ResponseEntity<ResponseDto<String>> updateStatus(@RequestBody EntityIdDto entityIdDto, BindingResult result) {
        if(result.hasErrors()) {
            return errorHandler.handleValidationErrors(result);
        }
        try{
            return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(
                    inventoryService.updateStatus(entityIdDto),null),HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping
    @RequestMapping(value = "/isStockExist")
    public ResponseEntity<ResponseDto<Boolean>> isStockExist(@RequestBody SkuCodeDto skuCodeDto, BindingResult result) {
        if(result.hasErrors()) {
            return errorHandler.handleValidationErrors(result);
        }
        try{
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(
                    inventoryService.isItemExist(skuCodeDto),null),HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping
    @RequestMapping(value = "/getAllInventory")
    public ResponseEntity<ResponseDto<List<InventoryDto>>> getAllInventory() {
         return new ResponseEntity<ResponseDto<List<InventoryDto>>>(new ResponseDto<List<InventoryDto>>(
                 inventoryService.getAllInventory(),null),HttpStatus.OK);
    }
}
