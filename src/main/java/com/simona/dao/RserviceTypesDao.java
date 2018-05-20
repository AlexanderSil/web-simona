package com.simona.dao;

import com.simona.model.RserviceTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RserviceTypesDao extends CrudRepository<RserviceTypes, Integer> {
}
