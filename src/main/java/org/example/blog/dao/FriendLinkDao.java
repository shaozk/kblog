package org.example.blog.dao;

import org.example.blog.pojo.FriendLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FriendLinkDao extends JpaRepository<FriendLink, String>, JpaSpecificationExecutor<FriendLink> {
    FriendLink findOneById(String friendLinkId);

    int deleteAllById(String friendLinkId);
}
