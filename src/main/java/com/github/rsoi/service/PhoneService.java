package com.github.rsoi.service;


import com.github.rsoi.domain.Phone;
import com.github.rsoi.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public void deletePhone (Long phoneId) {
        phoneRepository.deleteById(phoneId);
    }

    public List<Phone> getPhones() {
        return phoneRepository.findAll();
    }
}
