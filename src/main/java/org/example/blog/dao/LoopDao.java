package org.example.blog.dao;

import org.example.blog.pojo.Looper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoopDao extends JpaRepository<Looper, String>, JpaSpecificationExecutor<Looper> {

    Looper findOneById(String loopId);

    int deleteAllById(String looperId);
}
