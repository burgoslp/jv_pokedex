package com.pokedex.pokedex.dtos.statistic;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatisticDto {

    private Long id;
    @NotNull
    private Integer attack; 
    @NotNull
    private Integer defence; 
    @NotNull
    private Integer velocity;
    @NotNull
    private Integer life;
}
