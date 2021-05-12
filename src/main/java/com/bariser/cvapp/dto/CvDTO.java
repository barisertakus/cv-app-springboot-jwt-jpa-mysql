package com.bariser.cvapp.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class CvDTO {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date birthDate;

}
