package com.github.rsoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    @JsonProperty("phone_name")
    private String phoneName;
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("phone_min_price")
    private int phoneMinPrice;
    @JsonProperty("phone_max_price")
    private int phoneMaxPrice;
    @JsonProperty("screen_size")
    private double screenSize;
    @JsonProperty("rammax")
    private int rammax;
    @JsonProperty("rammin")
    private int rammin;
    @JsonProperty("has_sd_card_slot")
    private String hasSdCardSlot;
    @JsonProperty("image_link")
    private String imageLink;
}