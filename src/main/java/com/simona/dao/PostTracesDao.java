package com.simona.dao;

import com.simona.model.PostTraces;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTracesDao extends CrudRepository<PostTraces, Integer> {

    /**
     * For use parameters: (@Param("param") String param) - in query(:param)
     * @return
     */
    @Query("SELECT pt FROM PostTraces pt WHERE pt.timestamp=(SELECT MAX(timestamp) FROM PostTraces)")
    PostTraces findLastPostTraces();
}
