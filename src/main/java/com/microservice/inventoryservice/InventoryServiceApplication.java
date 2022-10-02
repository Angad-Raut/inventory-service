package com.microservice.inventoryservice;

import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import com.microservice.inventoryservice.util.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Date;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String args[]) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/message", "i18n/validationMessages");
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("SKUCODE-I");
			inventory.setQuantity(100);
			inventory.setStatus(Constants.ACTIVE_STATUS);
			inventory.setInsertTime(new Date());
			inventory.setUpdateTime(new Date());

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("SKUCODE-II");
			inventory1.setQuantity(10);
			inventory1.setStatus(Constants.ACTIVE_STATUS);
			inventory1.setInsertTime(new Date());
			inventory1.setUpdateTime(new Date());

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
