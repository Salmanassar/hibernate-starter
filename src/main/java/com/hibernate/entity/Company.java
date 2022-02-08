package com.hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "name")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @OrderColumn(name = "id")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @AttributeOverride(name = "language", @Column(name = "language"))
//    it uses for different name from entity and sql table column
    private List<LocaleInfo> localeInfos = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
