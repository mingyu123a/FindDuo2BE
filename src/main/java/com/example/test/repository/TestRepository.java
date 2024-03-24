package com.example.test.repository;



import com.example.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends JpaRepository<TestEntity, Integer> {
    TestEntity findById(int id);
    void deleteById(int id);
}

