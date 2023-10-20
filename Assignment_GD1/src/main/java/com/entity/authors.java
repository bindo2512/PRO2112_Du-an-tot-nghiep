package com.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer authorid;
    String authorname;
    @JsonIgnore
    @OneToMany(mappedBy = "authors")
    List<books> books;
}