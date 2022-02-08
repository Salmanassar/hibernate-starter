package com.hibernate.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class LocaleInfo {
    private String language;
    private String description;
}
