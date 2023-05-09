package com.github.rsoi.controller;

import com.github.rsoi.domain.Phone;
import com.github.rsoi.dto.PhoneDTO;
import com.github.rsoi.repository.PhoneRepository;
import com.github.rsoi.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneController.class)
public class PhoneControllerTest {

    @Autowired
    private PhoneController phoneController;

    //имитирует поведение реального объекта
    @MockBean
    private PhoneService phoneService;

    @MockBean
    private PhoneRepository phoneRepository;

    @Test
    public void testGetInternetShop() {
        ResponseEntity response = phoneController.getInternetShop();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Сервер работает ура!", response.getBody());
    }

    @Test
    public void testGetPhones() {

        List<Phone> phones = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setId(1L);
        phone1.setPhoneName("Phone 1");
        Phone phone2 = new Phone();
        phone2.setId(2L);
        phone2.setPhoneName("Phone 2");
        phones.add(phone1);
        phones.add(phone2);

        when(phoneService.getPhones()).thenReturn(phones);
        ResponseEntity<List<Phone>> response = phoneController.getPhones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(phones, response.getBody());
    }

    @Test
    void testGetPhonesFailure() {

        when(phoneService.getPhones()).thenThrow(new RuntimeException());
        ResponseEntity responseEntity = phoneController.getPhones();
        String responseMessage = (String) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Произошла ошибка", responseMessage);
    }

    @Test
    public void testAddPhone() {

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPhoneName("Phone 1");
        phoneDTO.setProducer("Producer 1");
        phoneDTO.setPhoneMinPrice(100);
        phoneDTO.setPhoneMaxPrice(200);
        phoneDTO.setScreenSize(5.0);
        phoneDTO.setRammin(2);
        phoneDTO.setRammax(4);
        phoneDTO.setHasSdCardSlot("true");
        phoneDTO.setImageLink("image-link");

        Phone phone = new Phone();
        phone.setId(1L);
        phone.setPhoneName("Phone 1");
        phone.setProducer("Producer 1");
        phone.setPhoneMinPrice(100);
        phone.setPhoneMaxPrice(200);
        phone.setScreenSize(5.0);
        phone.setRammin(2);
        phone.setRammax(4);
        phone.setHasSdCardSlot("true");
        phone.setImageLink("image-link");

        when(phoneRepository.save(Mockito.any(Phone.class))).thenReturn(phone);

        Phone addedPhone = phoneController.addPhone(phoneDTO);
        assertEquals(phone, addedPhone);
    }

    @Test
    public void testDeletePhone() {
        Long phoneId = 1L;
        ResponseEntity response = phoneController.deleteGoods(phoneId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Товар успешно удален!", response.getBody());
    }

//    @Test
//    public void testDeleteGoodsWithNonExistingId() {
//        Long id = 900L; // id, которого нет в базе данных
//        ResponseEntity response = phoneController.deleteGoods(id);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Произошла ошибка", response.getBody());
//    }


    @Test
    public void testGetPhonesByRamRange() {
        int ramMin = 4;
        int ramMax = 8;
        List<Phone> phones = Arrays.asList(
                new Phone(1L, "Phone 1", "Apple", 500, 1000, 6.5, 8, 4, "Yes", "image1.jpg"),
                new Phone(2L, "Phone 2", "Samsung", 300, 800, 6.2, 16, 8, "No", "image2.jpg"),
                new Phone(3L, "Phone 3", "Xiaomi", 200, 400, 6.0, 12, 6, "Yes", "image3.jpg")
        );

        when(phoneRepository.findPhonesByRamRange(ramMin, ramMax)).thenReturn(phones);

        List<Phone> expectedPhones = Arrays.asList(
                new Phone(1L, "Phone 1", "Apple", 500, 1000, 6.5, 8, 4, "Yes", "image1.jpg"),
                new Phone(3L, "Phone 3", "Xiaomi", 200, 400, 6.0, 12, 6, "Yes", "image3.jpg"),
                new Phone(2L, "Phone 2", "Samsung", 300, 800, 6.2, 16, 8, "No", "image2.jpg")
        );

        List<Phone> actualPhones = phoneController.getPhonesByRamRange(ramMin, ramMax);

        assertEquals(expectedPhones, actualPhones);
    }

    @Test
    void testSearchPhones() {
        Phone phone1 = new Phone();
        phone1.setPhoneName("Samsung Galaxy S20");
        Phone phone2 = new Phone();
        phone2.setPhoneName("iPhone 12");
        List<Phone> phones = Arrays.asList(phone1, phone2);

        when(phoneRepository.findByPhoneNameContainingIgnoreCase("phone")).thenReturn(phones);

        List<Phone> result = phoneController.searchPhones("phone");

        assertEquals(phones, result);
    }

    @Test
    public void testCountMatchingParameters() {

        Phone phone = new Phone();
        phone.setRammin(5);
        phone.setRammax(7);
        int rammin = 4;
        int rammax = 8;

        int matches = phoneController.countMatchingParameters(phone, rammin, rammax);
        assertEquals(1, matches, "Should have matched only on RAM");
    }

}