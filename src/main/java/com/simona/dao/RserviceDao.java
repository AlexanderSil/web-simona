package com.simona.dao;

import com.simona.model.Rservice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RserviceDao extends CrudRepository<Rservice, Integer> {
}
