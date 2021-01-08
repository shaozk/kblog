package org.example.blog.dao;

import org.example.blog.pojo.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelDao extends JpaRepository<Labels, String>, JpaSpecificationExecutor<Labels> {
}
