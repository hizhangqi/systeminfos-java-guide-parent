package com.systeminfos.springboot3demo.controller;

import com.systeminfos.springboot3demo.po.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/p")
public class ElasticsearchController {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PutMapping(value = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String save(@RequestBody(required = true) Person person) {

        IndexOperations indexOps = elasticsearchOperations.indexOps(Person.class);
        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
        }

        elasticsearchOperations.indexOps(Person.class);
        Person savedEntity = elasticsearchOperations.save(person);
        return savedEntity.getId();
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id") Long id) {
        boolean exists = elasticsearchOperations.exists(id.toString(), Person.class);
        Person person = new Person();
        person.setId(id.toString());
        if (exists) {
            person = elasticsearchOperations.get(id.toString(), Person.class);
        }
        return person;
    }

}