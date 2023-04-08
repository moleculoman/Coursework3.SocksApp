package com.example.socksapp.services.impl;

import com.example.socksapp.models.Color;
import com.example.socksapp.models.Size;
import com.example.socksapp.models.Socks;
import com.example.socksapp.services.SocksFileService;
import com.example.socksapp.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class SocksServiceImpl implements SocksService {

    private final SocksFileService socksFilesService;

    private static Map<Socks, Long> socksMap = new HashMap<>();

    public SocksServiceImpl(SocksFileService socksFilesService) {
        this.socksFilesService = socksFilesService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Socks addSocks(Socks socks, long quantity) {
        if (quantity > 0 && socks.getCottonPart() > 0 && socks.getCottonPart() <= 100) {
            socksMap.merge(socks, quantity, Long::sum);
            socksMap.putIfAbsent(socks, quantity);
            saveToFile();
        }
        return socks;
    }

    @Override
    public Socks editSocks(Socks socks, long quantity) {
        ObjectUtils.isNotEmpty(socksMap);
        if (quantity > 0 && socksMap.containsKey(socks)) {
            long number = socksMap.get(socks) - quantity;
            if (number >= 0) {
                socksMap.merge(socks, quantity, (a, b) -> a - b);
                socksMap.putIfAbsent(socks, quantity);
                saveToFile();
            } else {
                throw new UnsupportedOperationException("There are no socks in stock");
            }
        }
        return socks;
    }

    @Override
    public long getSocksNumByParam(Color color, Size size, int cottonMin, int cottonMax) {
        ObjectUtils.isNotEmpty(socksMap);
        long count = 0;
        if (cottonMin >= 0 && cottonMax >= 0 && cottonMax >= cottonMin) {
            for (Map.Entry<Socks, Long> entry : socksMap.entrySet()) {
                if (entry.getKey().getColor() == color && entry.getKey().getSizeOfSocks() == size &&
                        entry.getKey().getCottonPart() >= cottonMin && entry.getKey().getCottonPart() <= cottonMax) {
                    count += entry.getValue();
                }
            }
        }
        return count;
    }

    @Override
    public boolean deleteSocks(Socks socks, long quantity) {
        ObjectUtils.isNotEmpty(socksMap);
        if (quantity > 0 && socksMap.containsKey(socks)) {
            long number = socksMap.get(socks) - quantity;
            if (number >= 0) {
                socksMap.merge(socks, quantity, (a, b) -> a - b);
                socksMap.putIfAbsent(socks, quantity);
                saveToFile();
                return true;
            } else {
                throw new UnsupportedOperationException("There are no socks in stock");
            }
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            socksFilesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = socksFilesService.readFromFile("/socks.json");
            socksMap = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
