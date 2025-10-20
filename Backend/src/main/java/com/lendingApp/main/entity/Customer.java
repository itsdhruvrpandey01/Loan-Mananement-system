package com.lendingApp.main.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;

   @OneToMany(mappedBy = "customer")
   private List<Application> applications;

   @OneToMany(mappedBy = "customer")
   private List<Document> documents;
   
   @OneToOne
   @JoinColumn(name = "user_id", referencedColumnName = "userId")
   private User user;
//
//    @OneToMany
//    private List<Installment> installments;
}