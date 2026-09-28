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
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;

@RestController // Define a classe como um comum controlador REST que retorna resposta em JSON
@RequestMapping ("/user")// Define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated 

public class UserController {

    @Autowired 
    private UserService userService;

    @GetMapping ("/{id}") // Mapeia requisições HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable Long Id) { // Método para buscar usuário por id capturado da URL
        User obj = this.userService.findById(Id); // Invoca a buscar do usuário através do ID recebido
        return ResponseEntity.ok().body(obj); // Retorna código HTTP 200(pk) com o objeto User no corpo da resposta
    } // Fim do método findById

    @PostMapping // Mapeia requisições HTTP POST na rota base "/user" (Criação do novo usuário)
    public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj) { // Valida regra de CreateUSer e desserializa o corpo JSON

        this.userService.create(obj); // Chama a camada de serviço para persistir o novo usuário no Banco de Dados
        URI url = ServletUriComponentsBuilder.fromCurrentRequest() // obtém a rota da requisição atual 
        .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Adiciona o Id do usuário gerado no final do caminho da URI
        return ResponseEntity.created(url).build(); // Retorna código HTTP 201(Created) contendo a URL no cabeçalho location

    }

    @PutMapping("/{id}") // Mapeia requisições HTTP PUT na rota base "/user/{id}" (Atualização do usuário)
    public ResponseEntity<Void> update(@Validated(UpdateUser.class)@RequestBody User obj, @PathVariable Long id) { // Aplica a regra de UpdateUser e recebe ID e JSON

        obj.setId(id); // Garante que o ID do objeto a ser atualizado corresponde ao ID informado no parâmetro da URL 
        this.userService.update(obj); // Executa a atualização da senha do usuário no Banco de Dados
        return ResponseEntity.noContent().build(); // Retorna código HTTP 204(No content) indicando sucesso sem corpo de resposta

    }

        @DeleteMapping ("/{id}") // Mapeia requisições HTTP DELETE na rota "/user/{id}" (exclusão de usuário)
        public ResponseEntity<Void> delete(@PathVariable Long id) { // Captura o ID da URL a ser deletado
            this.userService.delete(id); // Invoca o método de deleção do serviço
            return ResponseEntity.noContent().build(); // Retorna código HTTP 204 (No content) confirmando a exclusão

        }

}