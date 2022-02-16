package com.hibernate.entity;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Manager extends User {
    private String projectName;

    @Builder
    public Manager(Long id, String userName, PersonInfo personInfo, Role role,
                   String info, Company company, Profile profile,
                   List<Chat> chats, String projectName) {
        super(id, userName, personInfo, role, info, company, profile, chats);
        this.projectName = projectName;
    }
}
