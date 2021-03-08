package com.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.annotation.TaxYearAware;
import com.sample.models.Person;
import com.sample.service.PersonService;


@RestController
@RequestMapping(value = "/person")
public class PersonRest {

    @Autowired private PersonService service;

    @TaxYearAware
    @RequestMapping(value = "/all")
    public Iterable<Person> getAll(){
        return service.findAll();
    }
    
    @RequestMapping(value = "/noannotation")
    public Iterable<Person> getAllSec(){
        return service.findAllSec();
    }
}
