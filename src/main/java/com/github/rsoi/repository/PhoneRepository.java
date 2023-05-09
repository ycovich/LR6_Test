package com.github.rsoi.repository;


import com.github.rsoi.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("phoneRepository")
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Query("SELECT p FROM Phone p WHERE p.rammin >= :rammin AND p.rammax <= :rammax")
    List<Phone> findPhonesByRamRange(@Param("rammin") int rammin, @Param("rammax") int rammax);

    List<Phone> findByPhoneNameContainingIgnoreCase(String phoneName);

}

