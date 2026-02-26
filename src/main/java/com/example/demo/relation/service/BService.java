package com.example.demo.relation.service;

import com.example.demo.relation.model.A;
import com.example.demo.relation.model.BDto;
import com.example.demo.relation.repository.BRepository;
import com.example.demo.relation.model.B;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BService {
    private final BRepository bRespository;

    public void read(Long idx) {
        B b = bRespository.findById(idx).orElseThrow();
    }

    public void readList() {
        List<B> b = bRespository.findAll();
    }

    public BDto.BRes req(Long aIdx, BDto.BReq dto) {
        B b = dto.toEntity(aIdx);

        b = bRespository.save(b);

        return BDto.BRes.from(b);
    }
}
