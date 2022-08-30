package com.dbsrud.webservice.service;

import com.dbsrud.webservice.controller.RequestDTO;
import com.dbsrud.webservice.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    public ArrayList<HashMap<String, Object>> findAll() {
        return memberMapper.findAll();
    }

//    public void createMember(RequestDTO requestDTO) {
//        memberMapper.createMember(requestDTO);
//    }
    public void createMember(Model model) {
        memberMapper.createMember(model);
    }
}
