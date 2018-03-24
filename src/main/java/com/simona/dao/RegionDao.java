package com.simona.dao;

import com.simona.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 3/19/18.
 */
@Repository
public interface RegionDao extends JpaRepository<Region, Long> {
}
