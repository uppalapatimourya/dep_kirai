package com.kirai.controller;

import com.kirai.DTO.KiraiDetailsDTO;
import com.kirai.model.ResponseModel;
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

    /**
     * API Exposed to save the Kirai Detais
     * @param kiraiDetailsDTO
     * @return
     */
    @PostMapping("/saveKiraiDetails")
    public ResponseEntity<String> saveKiraiDetails(@RequestBody KiraiDetailsDTO kiraiDetailsDTO){
        log.debug("Api Called :: saveKiraiDetails");
        return new ResponseEntity<>(kiraiDetailsService.saveKiraiDetails(kiraiDetailsDTO), HttpStatus.OK);
    }

    /**
     * API Exposed to Retrieve Kirai Details with pagination
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getKiraiDetails")
    public ResponseEntity<List<KiraiDetailsDTO>>  retrieveKiraiDetails(@RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(kiraiDetailsService.getKiraiDetails(page,size),HttpStatus.OK);
    }

    /**
     * Search Kirai Details using filed and value
     * @param fieldName
     * @param value
     * @return
     */
    @GetMapping("/filter")
    public ResponseEntity<List<KiraiDetailsDTO>> filterKiraiDetails(@RequestParam String fieldName,@RequestParam String value){
        return new ResponseEntity<>(kiraiDetailsService.filterKiraiDetails(fieldName, value),HttpStatus.OK);
    }
}
