package com.lendingApp.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collateral_type")
public class CollateralTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collateral_type_id")
    private Long collateralTypeId;

    @Column(nullable = false, unique = true)
    private String typeName;

    private String description; // optional
}
