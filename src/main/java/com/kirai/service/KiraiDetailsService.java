package com.kirai.service;

import com.kirai.DTO.KiraiDetailsDTO;
import com.kirai.model.Kirai;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KiraiDetailsService {

    String saveKiraiDetails(KiraiDetailsDTO kiraiDetails);

    List<KiraiDetailsDTO> getKiraiDetails(int page,int size);

    List<KiraiDetailsDTO> filterKiraiDetails(String filedName, String value);
}
