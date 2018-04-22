package com.simona.dao;

import com.simona.model.Station;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationDao extends CrudRepository<Station, Integer> {
}
