package com.example.test.service;


import com.example.test.DTO.DTO;
import com.example.test.repository.TestRepository;
import com.example.test.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository repository;
    public Iterable<TestEntity> getAllBoard(){
        return repository.findAll();
    }
    public TestEntity getBoard(int id) {
        return repository.findById(id);
    }
    public TestEntity setBoard(DTO dto){
        TestEntity entity = new TestEntity();
        String content = dto.getContent();
        String title = dto.getTitle();
        entity.setContent(content);
        entity.setTitle(title);
        return repository.save(entity);
    }
    public TestEntity updateBoard(int id, DTO dto) {
        if (repository.existsById(id)) {
            TestEntity entity = repository.findById(id);
            entity.setContent(dto.getContent());
            entity.setTitle(dto.getTitle());
            repository.save(entity);
            return entity;
        }
        return null;
    }
    public void deleteBoard(int id) {
        repository.deleteById(id);
    }

}
