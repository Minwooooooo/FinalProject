package com.example.demo.repository.util;

import com.example.demo.entity.util.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findAllByCategory(String category);

    List<Script> findAllByType(int type);
}
