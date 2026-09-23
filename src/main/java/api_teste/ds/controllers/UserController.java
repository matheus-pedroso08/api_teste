package api_teste.ds.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;//injeção automatica do Spring
import org.springframework.http.ResponseEntity;//importa a classe para montar a resposta HTTP completa(status, headers, )
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;//mapea requisições do tipo delete
import org.springframework.web.bind.annotation.GetMapping;//mapea requisições do tipo GET
import org.springframework.web.bind.annotation.PathVariable;//mapeia variaveis passadas diretamente via caminho URL
import org.springframework.web.bind.annotation.PostMapping;//mapeia requisições do tipo POST
import org.springframework.web.bind.annotation.PutMapping;//mapeia requisições do tipo PUT
import org.springframework.web.bind.annotation.RequestBody;//converste objetos JSON em objetos JAVA 
import org.springframework.web.bind.annotation.RequestMapping;//Importa anotação para definir o caminho/rota bas do controlador
import org.springframework.web.bind.annotation.RestController;//Importa anotação que define esta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;//Importa utilitario para gerar a URI ds requisição atual dinamicamente.

import api_teste.ds.models.User;
import api_teste.ds.models.User.createUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;

@RestController // Define a classe como um comum controlador REST que retorna resposta em JSON
@RequestMapping ("/user")// Define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated 

public class UserController {

    @Autowired 
    private UserService userService;

    @GetMapping ("/{id}")
    public ResponseEntity<User> findById(@PathVariable long Id){
        User obj=this.userService.findById(Id);
        return ResponseEntity.ok().body(obj);
    }//Fim do método FindbyId

    @PostMapping //Mapeia requesições HTTP POST na rota base"/user"(criação de novo usuario)
    public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj){
    this.userService.create(obj);
    URI url = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();    
    }

    @PutMapping("/(id")
    public ResponseEntity<Void> update(@Validated(UpdateUser.class)@RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();

    }

}