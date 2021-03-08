package com.sample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.sample.models.Person;

@Service
public interface PersonRepository extends CrudRepository<Person,Long> {
}
