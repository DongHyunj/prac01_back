package com.example.demo.relation.service;

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
}
