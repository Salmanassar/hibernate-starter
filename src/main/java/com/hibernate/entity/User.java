package com.hibernate.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="firstName",column=@Column(name="first_name")),
            @AttributeOverride(name="lastName",column=@Column(name="last_name")),
            @AttributeOverride(name="birthDay",column=@Column(name="birth_date"))
    })
    private PersonInfo personInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Type(type = "jsonb")
    private String info;
}
