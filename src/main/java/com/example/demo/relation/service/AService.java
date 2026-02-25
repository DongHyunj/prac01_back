package com.example.demo.relation.service;

import com.example.demo.relation.repository.ARespository;
import com.example.demo.relation.model.A;
import com.example.demo.relation.model.ADto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AService {
    private final ARespository aRespository;

    @Transactional(readOnly = true)
    public ADto.ARes read(Long idx) {
        A a = aRespository.findById(idx).orElseThrow();
        return ADto.ARes.from(a);
    }

    public void readList() {
        List<A> aList = aRespository.findAll();


    }
}
