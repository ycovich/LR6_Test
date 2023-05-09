package com.github.rsoi.service;

import com.github.rsoi.domain.Phone;
import com.github.rsoi.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneService.class)
public class PhoneServiceTest {

    @Autowired
    private PhoneService phoneService;

    @MockBean
    private PhoneRepository phoneRepository;

    @Test
    public void shouldDeletePhoneById() {
        // given
        doNothing().when(this.phoneRepository).deleteById(anyLong());

        // when
        this.phoneService.deletePhone(1L);

        // then
        verify(this.phoneRepository, times(1)).deleteById(eq(1L));
    }

    @Test
    public void shouldGetAllPhones() {
        // given
        List<Phone> phones = Arrays.asList(
                new Phone(1L, "Phone 1", "Apple", 500, 1000, 6.5, 8, 4, "Yes", "image1.jpg"),
                new Phone(2L, "Phone 2", "Samsung", 300, 800, 6.2, 16, 8, "No", "image2.jpg"),
                new Phone(3L, "Phone 3", "Xiaomi", 200, 400, 6.0, 12, 6, "Yes", "image3.jpg")

        );
        when(this.phoneRepository.findAll()).thenReturn(phones);

        // when
        List<Phone> result = this.phoneService.getPhones();

        // then
        verify(this.phoneRepository, times(1)).findAll();
        assertEquals(phones, result);
    }
}

