package com.example.java.application.services;

import com.example.java.domain.model.Budget;
import com.example.java.domain.model.FinancialOperation;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface FinancialOperationService {

    public List getAllFinancialOperationTypes();

    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation financialOperationToAdd);

    public Budget deleteFinancialOperation(UUID budgetId, FinancialOperation financialOperatioToDelete); 
    
    public FinancialOperation copyWithNewDate (FinancialOperation financialOperatioToCopy, Date newDateTime);
}
