package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Rifa;
import application.repository.RifaRepository;

@RestController
@RequestMapping("/rifas")
public class RifaController {

    @Autowired
    private RifaRepository rifaRepository;
    
    @GetMapping
    public Iterable<Rifa> getAll(){
        return rifaRepository.findAll();
    }
    @PostMapping
    public Rifa post(@RequestBody Rifa rifa) {
        return rifaRepository.save(rifa);
    }


    @GetMapping("/{id}")
    public Rifa getOne(@PathVariable long id) {
        Optional<Rifa> resultado = rifaRepository.findById(id);
        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rifa não encontrada"
            );
        }
        return resultado.get();
    }
    @PutMapping("/{id}")
    public Rifa put(@PathVariable long id, @RequestBody Rifa novosDados){
        Optional<Rifa> resultado = rifaRepository.findById(id);

        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tarefa não encontrada"
            );
        }

        resultado.get().setName(novosDados.getName());
        resultado.get().setValue(novosDados.getValue());
        resultado.get().setVendido(novosDados.isVendido());

        return rifaRepository.save(resultado.get());
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(!rifaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rifa não encontrada"
            );
        }

        rifaRepository.deleteById(id);
    }
}
