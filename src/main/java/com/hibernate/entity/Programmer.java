package com.hibernate.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("programmer")
@Entity
public class Programmer extends User {

    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, String userName, PersonInfo personInfo,
                      Role role, String info, Company company, Profile profile,
                      List<Chat> chats, Language language) {
        super(id, userName, personInfo, role, info, company, profile, chats);
        this.language = language;
    }
}
