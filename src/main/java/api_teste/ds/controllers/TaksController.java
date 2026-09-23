package api_teste.ds.controllers;

import java.net.URI;//Importa a classe URI para construir e manipular HTTP de novos recursos
import java.util.List;//Importa a interface list para manipular onde a classe controller está localizada 

import org.springframework.beans.factory.annotation.Autowired;//injeção automatica do spring 
import org.springframework.http.ResponseEntity;//importa a classe para montar a resposta HTTP com
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import api_teste.ds.models.Task;
import api_teste.ds.services.TaskService;

@RestController
@RequestMapping
@Validated
public class TaksController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable long id){
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/user/{userid")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable long userId){
        List<Task> objs = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(objs);
    }
    @GetMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) { //

        this.taskService.create(obj); // 
        URI url = ServletUriComponentsBuilder.fromCurrentRequest() // 
        .path("/{id}").buildAndExpand(obj.getId()).toUri(); // 
        return ResponseEntity.created(url).build();

    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@Validated @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id")
    public ResponseEntity<Void> delete(@PathVariable long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    

}
