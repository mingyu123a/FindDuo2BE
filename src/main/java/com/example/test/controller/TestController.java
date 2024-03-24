package com.example.test.controller;

import com.example.test.DTO.DTO;
import com.example.test.entity.TestEntity;
import com.example.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private TestService service;
    @GetMapping("/all")
    public Iterable<TestEntity> GetAllBoard(){
        return service.getAllBoard();
    }


    public int sum(int x, int y){
        return x+y;
    }

    @GetMapping("/view/{id}")
    public TestEntity getBoard(@PathVariable int id) {
        return service.getBoard(id);
    }
    @PostMapping("/create")
    public String setBoard(@RequestBody DTO dto){
        service.setBoard(dto);
        return "성공";
    }

    @PostMapping("/update/{id}")
    public TestEntity updateBoard(@PathVariable int id, @RequestBody DTO dto) {
        return service.updateBoard(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable int id) {
        service.deleteBoard(id);
    }

}
