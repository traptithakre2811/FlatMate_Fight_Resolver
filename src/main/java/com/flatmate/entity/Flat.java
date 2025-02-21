package com.flatmate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flatCode;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> users;
}
