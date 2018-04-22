package com.simona.dao;

import com.simona.model.ControlPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlPointDao extends CrudRepository<ControlPoint, Integer>{
}
