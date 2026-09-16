//Pacote onde está a classe de serviço no projeto 
package api_teste.ds.services;

//Importa List da biblioteca padrão do java para manipular coleções de objetos.
import java.util.List;
//Importa Optional, usado para tratar valores que podem não estar presentes (evita NullExceptionPointer)
import java.util.Optional;

//Importa a anotação do Spring para a injeção automatica de dependencias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskCallback;
//Importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;
//Importa a anotação para gerenciar transações no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//Importa o modelo.task
import api_teste.ds.models.Task;
//Importa o modelo.user
import api_teste.ds.models.User;
//Importa a interface do repositorio responsavel pelas operções no banco de dados
import api_teste.ds.repositories.TaskRepository;

//Anotação que indica para o Spring que essa classe contem as regras de negócio
@Service
public class TaskService {

    //Injeta automaticamente a instacia do TaskRepositiorio gerenciado pelo Spring
    @Autowired 
    private TaskRepository taskRepository;

    //Injeta automaticamente a Instacia do UserService para validar o usário
    @Autowired 
    private UserService userService;
    //Executa a busca no banco, retorna um optional contendo (ou não) a Task
    public Task findById(long Id){
        Optional<Task> task = this.taskRepository.findById(Id);

        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada! Id:"+ Id + "Tipo:" + Task.class.getName()
        ));

    }
    
    //metodo para buscar todas as tarefas vinculadas a um determinado usuario 
    public List<Task> findByUserId(Long UserId){

        //chamma o UserService para garantir que o usuario existe no banco(lança exeção se não existir)
        this.UserService.findById(UserId);

        List<Task> tasks = this.taskRepository.findByUser(UserId);

            //Retorna a lista de tarefa
            return tasks;
    
            //Garante que a criação ocorra dentro de uma transação de banco de dados(rolback automatico se falhar)
            @Transactional
            public Task create(Task obj){

                User user = this.userService.findById(obj.getUser().getId());

                obj.setId(id:null);

                obj.setuser(user);

                obj = this.taskRepository.save(obj);

                return obj;

            }

            @Transactional 
            public Task update()
    
}
