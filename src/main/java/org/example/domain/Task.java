package org.example.domain;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "tinyint")
    private Status status;
}
