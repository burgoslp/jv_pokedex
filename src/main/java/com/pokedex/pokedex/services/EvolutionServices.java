package com.pokedex.pokedex.services;
import static com.pokedex.pokedex.util.MapToDto.evolutionRelatedToDto;
import static com.pokedex.pokedex.util.MapToDto.evolutionToDto;
import static com.pokedex.pokedex.util.MapToDto.evolutionToDtoList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.pokedex.pokedex.dtos.Evolution.CreateEvolutionDto;
import com.pokedex.pokedex.dtos.Evolution.EvolutionDto;
import com.pokedex.pokedex.dtos.json.JsonApiresponse;
import com.pokedex.pokedex.exceptions.APIError;
import com.pokedex.pokedex.exceptions.APIException;
import com.pokedex.pokedex.models.Evolution;
import com.pokedex.pokedex.models.Pokemon;
import com.pokedex.pokedex.repositories.IEvolutionRepository;
import com.pokedex.pokedex.repositories.IPokemonRepository;
import com.pokedex.pokedex.services.interfaces.IEvolutionServices;

@Service
public class EvolutionServices implements IEvolutionServices{

    @Autowired
    IEvolutionRepository er;

    @Autowired
    IPokemonRepository pr;

    @Override
    public JsonApiresponse findAll() {
      List<Evolution> evolutions= (List<Evolution>) er.findAll();
      return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));
    }

    @Override
    public JsonApiresponse findAllByOrderByWeightDesc() {
       List<Evolution> evolutions = er.findAllByOrderByWeightDesc();
       return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));

    }

    @Override
    public JsonApiresponse findAllByOrderByWeightAsc() {
        List<Evolution> evolutions=er.findAllByOrderByWeightAsc();
        return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));
    }
    

    @Override
    public JsonApiresponse findAllByOrderByHeightDesc() {
       List<Evolution> evolutions = er.findAllByOrderByHeightDesc();
       return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));
    }

    @Override
    public JsonApiresponse findAllByOrderByHeightAsc() {
        List<Evolution> evolutions = er.findAllByOrderByHeightAsc();
        return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));
    }

    @Override
    public JsonApiresponse findById(Long id) {
       Optional<Evolution> evolution= er.findById(id); 
        return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionRelatedToDto(evolution.orElseThrow(()->new APIException(APIError.EVOLUTION_BYID_NOT_FOUND))));
      
    }

    @Override
    public JsonApiresponse  findByNameLikeIgnoreCaseOrCodeLikeIgnoreCase(String name, String code) {
        List<Evolution> evolutions=er.findByNameLikeIgnoreCaseOrCodeLikeIgnoreCase("%"+name+"%","%"+code+"%");
        if (evolutions.isEmpty()) {
            throw new APIException(APIError.EVOLUTION_VALUE_NOT_FOUND);
        }
        return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),evolutionToDtoList(evolutions));
    }

    @Override
    public JsonApiresponse save(CreateEvolutionDto createEvolutionDto) {
      Pokemon pokemon= pr.findById(createEvolutionDto.getPokemonId()).orElseThrow(() -> new APIException(APIError.POKEMON_BYID_NOT_FOUND));    
        Evolution evolution= Evolution.builder()
                                                .name(createEvolutionDto.getName())
                                                .description(createEvolutionDto.getDescription())
                                                .height(createEvolutionDto.getHeight())
                                                .weight(createEvolutionDto.getWeight())
                                                .code(createEvolutionDto.getCode())
                                                .image(createEvolutionDto.getImage())
                                                .pokemon(pokemon).build();

      return new JsonApiresponse(HttpStatus.CREATED.value(),HttpStatus.CREATED.getReasonPhrase(),evolutionRelatedToDto(er.save(evolution)));
    }

    @Override
    public JsonApiresponse update(Long id, EvolutionDto evolutionDTO) {
      Evolution evolution= er.findById(id).orElseThrow(()->new APIException(APIError.EVOLUTION_BYID_NOT_FOUND));
      
      evolution.setName(evolutionDTO.getName() != null ? evolutionDTO.getName() : evolution.getName());
      evolution.setDescription(evolutionDTO.getDescription() != null ? evolutionDTO.getDescription() : evolution.getDescription());
      evolution.setHeight(evolutionDTO.getHeight() != null ? evolutionDTO.getHeight() : evolution.getHeight());
      evolution.setWeight(evolutionDTO.getWeight() != null ? evolutionDTO.getWeight() : evolution.getWeight());
      evolution.setCode(evolutionDTO.getCode() != null ? evolutionDTO.getCode() : evolution.getCode());
      evolution.setImage(evolutionDTO.getImage() != null ? evolutionDTO.getImage() : evolution.getImage());

      return new JsonApiresponse(HttpStatus.CREATED.value(),HttpStatus.CREATED.getReasonPhrase(),evolutionToDto(er.save(evolution)));
    }

    @Override
    public JsonApiresponse delete(Long id) {
        Evolution evolution= er.findById(id).orElseThrow(()-> new APIException(APIError.EVOLUTION_BYID_NOT_FOUND));
        er.delete(evolution);
        return new JsonApiresponse(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Se ha eliminado la evolución con exito.");
    }

}
