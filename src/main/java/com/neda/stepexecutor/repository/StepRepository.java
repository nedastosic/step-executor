package com.neda.stepexecutor.repository;

import com.neda.stepexecutor.entity.Step;
import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
    Step findByName(String stepName);
}
