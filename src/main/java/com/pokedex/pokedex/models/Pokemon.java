package com.pokedex.pokedex.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pokemons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "pokemon",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evolution> evolutions;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Type> types;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
        name = "pokemons_weaknesses",
        joinColumns = @JoinColumn(name = "pokemons_id"),
        inverseJoinColumns = @JoinColumn(name = "types_id")
    )
    private List<Type> weaknesses;
    @OneToOne(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Statistic statistic;
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;    
    @Column(length = 1000)
    @NotBlank
    @Size(min=1, message="debes agregar una descripción")   
    private String description;
    @NotNull
    private Double height;
    @NotNull
    private Double weight;
    @NotNull
    @Size(min=5, max = 10, message = "El formato del codigo es de #0000")
    @Column(unique = true)
    private String code;
    @NotBlank
    private String image;
}
