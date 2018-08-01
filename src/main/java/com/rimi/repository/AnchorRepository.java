package com.rimi.repository;

import com.rimi.model.Anchor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnchorRepository extends JpaRepository<Anchor,String> {
}
