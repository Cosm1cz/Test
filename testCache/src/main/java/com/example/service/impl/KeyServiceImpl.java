package com.example.service.impl;

import com.example.dao.KeyDao;
import com.example.entity.DataPair;
import com.example.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyServiceImpl implements KeyService {
    @Autowired
    private KeyDao keydao;

    public DataPair selectValue(String key) {
        DataPair dataPair = keydao.selectValue(key);
        return dataPair;
    }
}
