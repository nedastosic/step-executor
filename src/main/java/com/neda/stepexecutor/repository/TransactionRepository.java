package com.neda.stepexecutor.repository;

import com.neda.stepexecutor.entity.Step;
import com.neda.stepexecutor.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByStep(Step step);
}
