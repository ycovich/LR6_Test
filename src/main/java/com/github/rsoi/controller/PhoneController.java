package com.github.rsoi.controller;


import com.github.rsoi.domain.Phone;
import com.github.rsoi.dto.PhoneDTO;
import com.github.rsoi.repository.PhoneRepository;
import com.github.rsoi.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/phonecreator")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private PhoneRepository phoneRepository;

    @GetMapping()
    public ResponseEntity getInternetShop() {
        try {
            return ResponseEntity.ok("Сервер работает ура!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/readPhone")
    public ResponseEntity getPhones() {
        try {
            return ResponseEntity.ok(phoneService.getPhones());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }


    @PostMapping("/addPhone")
    public Phone addPhone(@RequestBody PhoneDTO phoneDTO) {
        Phone phone = new Phone();
        phone.setPhoneName(phoneDTO.getPhoneName());
        phone.setProducer(phoneDTO.getProducer());
        phone.setPhoneMinPrice(phoneDTO.getPhoneMinPrice());
        phone.setPhoneMaxPrice(phoneDTO.getPhoneMaxPrice());
        phone.setScreenSize(phoneDTO.getScreenSize());
        phone.setRammin(phoneDTO.getRammin());
        phone.setRammax(phoneDTO.getRammax());
        phone.setHasSdCardSlot(phoneDTO.getHasSdCardSlot());
        phone.setImageLink(phoneDTO.getImageLink());
        return phoneRepository.save(phone);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGoods(@PathVariable Long id) {
        try {
            phoneService.deletePhone(id);
            return ResponseEntity.ok("Товар успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/getRam")
    public List<Phone> getPhonesByRamRange(@RequestParam int rammin, @RequestParam int rammax) {
        List<Phone> phones = phoneRepository.findPhonesByRamRange(rammin, rammax);
        Map<String, List<Phone>> phonesByManufacturer = phones.stream().collect(Collectors.groupingBy(Phone::getProducer));
        List<Phone> result = new ArrayList<>();
        phonesByManufacturer.forEach((manufacturer, phoneList) -> {
            phoneList.sort((p1, p2) -> {
                int p1Matches = countMatchingParameters(p1, rammin, rammax);
                int p2Matches = countMatchingParameters(p2, rammin, rammax);
                return Integer.compare(p2Matches, p1Matches);
            });
            result.addAll(phoneList);
        });
        return result;
    }
    public int countMatchingParameters(Phone phone, int rammin, int rammax) {
        int matches = 0;
        if (phone.getRammin() >= rammin && phone.getRammax() <= rammax) {
            matches++;
        }
        return matches;
    }

    @GetMapping("/getPhoneNames")
    public List<Phone> searchPhones(@RequestParam("query") String query) {
        List<Phone> phones = phoneRepository.findByPhoneNameContainingIgnoreCase(query);
        return phones;
    }

}
