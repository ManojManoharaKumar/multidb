package com.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.models.Person;
import com.sample.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository repo;

	@Transactional
	public Iterable<Person> findAll() {
		return repo.findAll();
	}
	
	@Transactional
	public Iterable<Person> findAllSec() {
		return repo.findAll();
	}

}
