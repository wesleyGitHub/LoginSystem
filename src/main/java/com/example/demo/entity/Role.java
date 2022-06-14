package com.example.demo.entity;

import com.example.demo.entity.AppUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    private Long id;
    private String name;
    private Long appUserId;

    public Role(String roleName,
                Long appUserId ) {
        this.name = roleName;
        this.appUserId = appUserId;
    }
}
