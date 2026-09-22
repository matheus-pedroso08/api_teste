//Pacote onde está a classe de serviço do projeto
package api_teste.ds.services;

//Importa List da biblioteca padrão do java para manipular coleções de objetos
import java.util.List;

//Importa Optional, usado para tratar valores que podem não estar presentes (Evitta NullExceptionalPointer)
import java.util.Optional;

import javax.management.RuntimeErrorException;

//Importa a anotação do Spring para a injeção automática de dependencias
import org.springframework.beans.factory.annotation.Autowired;

//Importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;

//Importa a anotação para gerencias transações no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//Importa o models.Task
import api_teste.ds.models.Task;

//Importa o models.User
import api_teste.ds.models.User;

//Importa a interface do repositório responsável pelas operações no banco de dados
import api_teste.ds.repositories.TaskRepository;
import jakarta.persistence.Id;

//Anotação que indica para o Spring que essa classe contém as regras de negócio
@Service

public class TaskService {

    //Injeta automaticamente a instancia do TaskRepository gerenciado pelo String
    @Autowired 
    private TaskRepository taskRepository;
        //Injeta automaticamente a instancia do UserService para validar o usuário
        @Autowired 
        private UserService UserService;

    //Método para buscar task apartir do ID
    public Task findById(Long Id){
        //Executa a busca no banco de dados e retorna um Optional contendo (ou não) a Task.
        Optional<Task> task = this.taskRepository.findById(Id);

        //Se a tarefa existir, retorna o objeto, se estiver vazio, lança um RunTimeException
        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada! Id: " + Id + ", Tipo: " + Task.class.getName() + "."
        ));


    }   

    //Método para buscar todas as terfas vinculadas a um determinado usuário
    public List<Task> findByUserId(Long UserId) {
        this.UserService.findById(UserId);
    
        List<Task> tasks = this.taskRepository.findByUser_Id(UserId);
    
        return tasks;
    }


        //Garante que a criação ocorra dentro de uma transação de banco de dados (rolback automático se falhar)
        @Transactional
        public Task create(Task obj){

            //Valida se o usuário informado no objeto realmente existe no banco e recupera seus dados
            User user = this.UserService.findById(obj.getUser().getId());

            //Define o ID como NULL para garantir que o JPA realize um inserção(INSERT) e não uma atualização
            obj.setId( null);

            //Associa a entidade User completa e validada a tarefa
            obj.setUser(user);

            //Salva a nova tarefa no banco de dados e atualiza 'obj' com o ID gerado
            obj = this.taskRepository.save(obj);

            //Retorna a tarefa salva
            return obj;

        }

        //Garante que a atualização ocorra dentro de uma transação isolada no banco
        @Transactional
        public Task update(Task obj){

            //Reaproveita o findById para verificar se a tarefa atualizada existe realmente
            Task newObj = findById(obj.getId());

            //Atualiza apenas o campo descrição do objeto persistido com o novo valor 
            newObj.setDescription(obj.getDescription());

            //Salva a atualização no banco de dados e retorna o objeto atualizado
            return this.taskRepository.save(newObj);

        }

        public void delete(long Id){

            findById(Id);

            try{

                this.taskRepository.deleteById(Id);
            } catch (Exception e){
                throw new RuntimeException("Não é possivel excluir pois não há tarefas relacionadas");
            }

    }  

}

