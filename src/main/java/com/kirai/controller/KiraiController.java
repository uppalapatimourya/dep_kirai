package com.kirai.controller;

import com.kirai.DTO.KiraiDetailsDTO;
import com.kirai.model.DhalariDetails;
import com.kirai.model.ResponseModel;
import com.kirai.model.RiceMill;
import com.kirai.service.KiraiDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/kirai")
public class KiraiController {

    @Autowired
    private KiraiDetailsService kiraiDetailsService;

    @PostMapping("/saveKiraiDetails")
    public ResponseEntity<String> saveKiraiDetails(@RequestBody KiraiDetailsDTO kiraiDetailsDTO){
        log.debug("Api Called :: saveKiraiDetails");
        return new ResponseEntity<>(kiraiDetailsService.saveKiraiDetails(kiraiDetailsDTO), HttpStatus.OK);
    }

    @GetMapping("/getKiraiDetails")
    public ResponseEntity<List<KiraiDetailsDTO>>  retrieveKiraiDetails(@RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(kiraiDetailsService.getKiraiDetails(page,size),HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<KiraiDetailsDTO>> filterKiraiDetails(@RequestParam String fieldName,@RequestParam String value){
        return new ResponseEntity<>(kiraiDetailsService.filterKiraiDetails(fieldName, value),HttpStatus.OK);
    }

    @GetMapping("/getAllRiceMills")
    public ResponseEntity<List<RiceMill>> getAllRiceMills() {
        log.debug("Api Called :: getAllRiceMills");
        List<RiceMill> riceMills = kiraiDetailsService.getAllRiceMills();
        return ResponseEntity.ok(riceMills);
    }

    @GetMapping("/getAllDhalariDetails")
    public ResponseEntity<List<DhalariDetails>> getAllDhalariDetails() {
        List<DhalariDetails> dhalariDetails = kiraiDetailsService.getAllDhalariDetails();
        return ResponseEntity.ok(dhalariDetails);
    }

}
