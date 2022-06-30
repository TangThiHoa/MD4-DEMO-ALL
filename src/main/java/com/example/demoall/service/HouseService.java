package com.example.demoall.service;

import com.example.demoall.model.House;
import com.example.demoall.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class HouseService implements IHouseService {
    @Autowired
    HouseRepository houseRepository;
    @Override
    public void save(House house) {
        houseRepository.save(house);

    }

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        houseRepository.deleteById(id);

    }
}
