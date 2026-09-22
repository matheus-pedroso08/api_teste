package api_teste.ds.services;

//Importa Optional, usado para tratar valores que podem não estar presentes (Evita NullExceptionalPointer)
import java.util.Optional;

import javax.annotation.processing.SupportedAnnotationTypes;

//Importa a anotação do Spring para a injeção automática de dependencias
import org.springframework.beans.factory.annotation.Autowired;

//Importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;

//Importa a anotação para gerencias transações no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//Importa o models.Task
import api_teste.ds.models.Task;
import api_teste.ds.models.User;
//Importa a interface do repositório responsável pelas operações no banco de dados
import api_teste.ds.repositories.UserRepository;

//Importa a interface do repositório responsável pelas operações no banco de dados
import api_teste.ds.repositories.TaskRepository;

//Anotação que indica no Spring que essa classe contém as regras de negócios da entidade User
@Service
public class UserService {
    

    @Autowired
    private UserRepository userRepository;

        @Autowired
        private TaskRepository taskRepository;

    public User findById(Long Id){

        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(()-> new RuntimeException(
            "Usuário não encontrado! Id: " + Id + ", Tipo: " + User.class.getName()
        ));

    }

    @Transactional
    public User create(User obj){
    obj.setId(null);
    obj = this.userRepository.save(obj);
    return obj;
}

    @Transactional
    public User update(User obj){

    User newObj = findById(obj.getId());

    newObj.setUsername(obj.getUsername());
    newObj.setPassword(obj.getPassword());

    return this.userRepository.save(newObj);
}

    public void delete(Long Id){

        findById(Id);

        try {

            this.userRepository.deleteById(Id);

        } catch (Exception e) {

            throw new RuntimeException("Não é possível excluir pois não há entidades relacionadas");

        }
    }
}