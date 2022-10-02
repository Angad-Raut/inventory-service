package com.microservice.inventoryservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:/error-code.properties")
public class ErrorCode {
	@Value("${DATABASE_CONSTRAINT_VIOLATION_ISSUE_OCCURRED}")
	public String DATABASE_CONSTRAINT_VIOLATION_ISSUE_OCCURRED;
	@Value("${INVENTORY_RECORD_ALREADY_PRESENT}")
	public String INVENTORY_RECORD_ALREADY_PRESENT;
	@Value("${INVENTORY_RECORD_NOT_FOUND}")
	public String INVENTORY_RECORD_NOT_FOUND;
}
