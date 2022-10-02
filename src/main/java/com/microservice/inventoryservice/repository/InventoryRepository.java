package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "select sku_code from inventory_data where sku_code=:skuCode" ,nativeQuery = true)
    String getSkuCode(@Param("skuCode")String skuCode);

    @Query(value = "select * from inventory_data where id=:id",nativeQuery = true)
    Inventory getById(@Param("id")Long id);

    @Modifying
    @Transactional
    @Query(value = "update inventory_data set status=:status where id=:id", nativeQuery = true)
    Integer updateStatus(@Param("id")Long id, @Param("status")Boolean status);
}
