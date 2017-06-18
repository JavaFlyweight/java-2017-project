package com.example.java.application.services;

import com.example.java.commons.enums.FinancialOperationType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.FinancialOperation;
import java.util.Date;
import java.util.UUID;

public interface FinancialOperationService {

    public FinancialOperationType [] getAllFinancialOperationTypes();

    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation financialOperationToAdd);

    public Budget deleteFinancialOperation(UUID budgetId, FinancialOperation financialOperatioToDelete); 
    
    public Budget addCopiedFinancialOperationWithNewDateToBudget (FinancialOperation financialOperatioToCopy, Date newDateTime, UUID budgetId);
}
