package com.example.dao;

import com.example.entity.DataPair;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KeyDao {
    DataPair selectValue(String key);
}
