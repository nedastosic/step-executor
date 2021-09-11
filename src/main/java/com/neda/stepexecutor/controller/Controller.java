package com.neda.stepexecutor.controller;

import com.neda.stepexecutor.entity.Step;
import com.neda.stepexecutor.entity.Transaction;
import com.neda.stepexecutor.repository.StepRepository;
import com.neda.stepexecutor.repository.TransactionRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/stepexecutor")
public class Controller {

    @Autowired
    private StepRepository stepRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/step")
    public ResponseEntity createStep(@RequestBody Step step) {

        try {
            if (stepRepository.findByName(step.getName()) != null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Step with this name already exists");
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(stepRepository.save(step));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }


    }

    @PostMapping("/step/{stepName}")
    ResponseEntity executeStep(@PathVariable("stepName") String stepName, @RequestBody String priceInJSON) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jsonObject = new JSONObject(priceInJSON);
            double price = jsonObject.getDouble("price");
            String transactionStatus;

            Step step = stepRepository.findByName(stepName);

            if(step == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Step does not exist");
            }

            try{
                restTemplate.getForEntity(step.getUrl() + "?price=" + price + "&stepName=" + step.getName(),
                        String.class);
                transactionStatus = "SUCCESS";
            }catch(HttpStatusCodeException e){
                transactionStatus = "FAILURE";
            }

            Transaction transaction = new Transaction(price, new Timestamp(System.currentTimeMillis()), transactionStatus, step);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(transactionRepository.save(transaction));
        } catch (JSONException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Bad input body request");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/step/{stepName}/transactions")
    public ResponseEntity getAllTransactionsByStepName(@PathVariable("stepName") String stepName) {
        Step step = stepRepository.findByName(stepName);

        List<Transaction> transactionList = transactionRepository.findAllByStep(step);

        if (step == null || transactionList.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionList);
    }

}
