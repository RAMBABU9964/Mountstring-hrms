package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.EmpDocs;
import com.example.demo.repository.EmpDocsRepository;

@Service
public class EmpDocsService {
	
	@Autowired
    private EmpDocsRepository empDocsRepository;

   

    public void save(EmpDocs empDocs) {
        empDocsRepository.save(empDocs);
    }
}
