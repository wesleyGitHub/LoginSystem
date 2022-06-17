package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APP_ROLE")
public class Role {

    @Id
    @SequenceGenerator(
            name = "app_role_sequence",
            sequenceName = "app_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_role_sequence"
    )
    private Long id;
    private String name;
}
