package com.bariser.cvapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id"),
        })
public class Cv {
    @Id
    @SequenceGenerator(
            name = "cv_sequence",
            sequenceName = "cv_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cv_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Long getUser() {
        return user.getId();
    }
}
