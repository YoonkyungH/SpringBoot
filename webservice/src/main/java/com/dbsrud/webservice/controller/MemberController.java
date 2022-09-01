package com.dbsrud.webservice.controller;

import com.dbsrud.webservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RestController
@Controller
//@RequestMapping(value = "/api/v1/app/")
@RequestMapping
public class MemberController {

    @Autowired
    MemberService memberService;

//    @RequestMapping(value = "findAll", method = RequestMethod.POST)
//    public ResponseEntity<?> findAll() {
//        ResponseDTO responseDTO = new ResponseDTO();
//        responseDTO.setResultCode("S0001");
//        responseDTO.setRes(memberService.findAll());
//
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }

    @GetMapping("/memberList")
    public String findAll(Model model) {

        model.addAttribute("memberList", memberService.findAll());

        return "memberList";
    }

    @PostMapping("/")
    public String createMember(Model model,
                               @RequestParam(value = "id") Long id,
                               @RequestParam(value = "age") int age,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "user_id") String user_id,
                               @RequestParam(value = "username") String username)
    {

        System.out.println("MemberController.createMember");

        model.addAttribute("id", id);
        model.addAttribute("age", age);
        model.addAttribute("password", password);
        model.addAttribute("user_id", user_id);
        model.addAttribute("username", username);

        memberService.createMember(model);

        return "index";
    }
}
