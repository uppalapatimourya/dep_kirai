package com.kirai.serviceImpl;

import com.kirai.DTO.KiraiDetailsDTO;
import com.kirai.model.DhalariDetails;
import com.kirai.model.Kirai;
import com.kirai.model.RiceMill;
import com.kirai.repository.DhalariRepository;
import com.kirai.repository.MongoDBRepository;
import com.kirai.repository.RiceMillRepository;
import com.kirai.service.KiraiDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class KiraiDetailsServiceImpl implements KiraiDetailsService {

    @Autowired
    private MongoDBRepository mongoDBRepository;

    @Autowired
    private RiceMillRepository riceMillRepository;

    @Autowired
    private DhalariRepository dhalariRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String saveKiraiDetails(KiraiDetailsDTO kiraiRequest) {
        String result=null;
        try {
            String KLNo=null;
            if(kiraiRequest.getKLNO()!=null){
                KLNo=kiraiRequest.getKLNO();
            }
            else {
                long totalCount = mongoDBRepository.count() + 1;
                KLNo = "KL" + totalCount;
            }
            Kirai kirai = Kirai.builder().kiraiDetails(kiraiRequest.getKiraiDetails())
                    .dhalariDetails(kiraiRequest.getDhalariDetails())
                    .loadingDetails(kiraiRequest.getLoadingDetails())
                    .lorryDetails(kiraiRequest.getLorryDetails())
                    .id(KLNo)
                    .notes(kiraiRequest.getNotes())
                    .instructions(kiraiRequest.getInstructions())
                    .loadingDate(kiraiRequest.getLoadingDate())
                    .mediator(kiraiRequest.getMediator())
                    .reachedDate(kiraiRequest.getReachedDate())
                    .weightageDetails(kiraiRequest.getWeightageDetails())
                    .riceMill(kiraiRequest.getRiceMill())
                    .transportOffices(kiraiRequest.getTransportOffices())
                    .build();
            riceMillRepository.save(kirai.getRiceMill());
            dhalariRepository.save(kirai.getDhalariDetails());

            Kirai savedEntity = mongoDBRepository.save(kirai);

            if (savedEntity != null) {
                result = "Kirai Details Saved Successfully and Id is : " + savedEntity.getId();
            }
        } catch (Exception e) {
            result="Exception occurred while saving data in database and Exception is "+e.getLocalizedMessage();
        }
        return result;
    }

    @Override
    public List<KiraiDetailsDTO> getKiraiDetails(int page,int size) {
        List<KiraiDetailsDTO> result=new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
       Page<Kirai> kiraiPageDetails =mongoDBRepository.findAll(pageable);
       List<Kirai> kiraiDetails=kiraiPageDetails.getContent();
       if(!CollectionUtils.isEmpty(kiraiDetails)){
           kiraiDetails.forEach(kirai->{
               KiraiDetailsDTO kiraiDetailsDTO=mapToDTA(kirai);
               result.add(kiraiDetailsDTO);
           });
       }
       return result;
    }

    @Override
    public List<KiraiDetailsDTO> filterKiraiDetails(String fieldName, String value) {
        List<KiraiDetailsDTO> result=new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where(fieldName).is(value));
        List<Kirai> kiraiDB=mongoTemplate.find(query,Kirai.class);
        if(!CollectionUtils.isEmpty(kiraiDB)){
           kiraiDB.forEach(kirai -> {
               KiraiDetailsDTO kiraiDetailsDTO=mapToDTA(kirai);
               result.add(kiraiDetailsDTO);
           });
        }
        return result;
    }

    private KiraiDetailsDTO mapToDTA(Kirai kirai){
        return KiraiDetailsDTO.builder()
                .kiraiDetails(kirai.getKiraiDetails())
                .dhalariDetails(kirai.getDhalariDetails())
                .loadingDetails(kirai.getLoadingDetails())
                .lorryDetails(kirai.getLorryDetails())
                .weightageDetails(kirai.getWeightageDetails())
                .notes(kirai.getNotes())
                .instructions(kirai.getInstructions())
                .KLNO(kirai.getId())
                .loadingDate(kirai.getLoadingDate())
                .reachedDate(kirai.getReachedDate())
                .mediator(kirai.getMediator())
                .riceMill(kirai.getRiceMill())
                .transportOffices(kirai.getTransportOffices())
                .build();
    }

    public List<RiceMill> getAllRiceMills() {
        List<RiceMill> riceMillList=  riceMillRepository.findAll();
        log.info("List of  RiceMills : " + riceMillList);
        return riceMillList;
    }

    public List<DhalariDetails> getAllDhalariDetails() {
        return dhalariRepository.findAll();
    }
}
