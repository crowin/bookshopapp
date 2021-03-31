package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long> {
}
