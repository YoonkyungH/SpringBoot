package com.dbsrud.webservice.mapper;

import com.dbsrud.webservice.controller.RequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
@Repository
public interface MemberMapper {

    ArrayList<HashMap<String, Object>> findAll();

//    void createMember(RequestDTO requestDTO);
    void createMember(Model model);

}
