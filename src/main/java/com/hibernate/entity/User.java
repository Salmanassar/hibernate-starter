package com.hibernate.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table(name = "users", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public abstract class User implements Comparable<User>, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name")),
            @AttributeOverride(name = "birthDay", column = @Column(name = "birth_date"))
    })
    private PersonInfo personInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Type(type = "jsonb")
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude //nevertheless, by default, it would be company_id
    private Company company;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @ToString.Exclude  //  the entity (profile) has to in the database
    private Profile profile;

//    @Builder.Default
    @ManyToMany()
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @ToString.Exclude
    private List<Chat> chats = new ArrayList<>();

    public void addChat(Chat chat){
        chats.add(chat);
        chat.getUsers().add(this);
    }

    @Override
    public int compareTo(User o) {
        return userName.compareTo(o.userName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
