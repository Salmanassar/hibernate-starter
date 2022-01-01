package com.hibernate.entity;

import com.hibernate.coverter.BirthdayConvertor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonInfo {
    private String firstName;
    private String lastName;
    @Convert(converter = BirthdayConvertor.class)
    private Birthday birthDay;
}
