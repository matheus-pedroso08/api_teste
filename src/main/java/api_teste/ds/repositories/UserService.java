package api_teste.ds.services;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.ds.models.Task;
import api_teste.ds.repositories.TaskRepository;
import api_teste.ds.repositories.UserRepository;

@Service 
public class UserService{

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private TaskRepository taskRepository;

    public User findById(Long Id){

        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow()-> new RuntimeException(
            "Usuario não encontrado!" + Id +"Tipo:" + User.class.getName()
        ));
    }

    @Transactional 
    public User create(USer obj){

        obj.setId(null);

        obj.this.userRepository.save(obj);

        this.taskRepository.saveAll(obj,getClass());

        return obj;
    }

    @Transactional 

    public user update(User obj){

        User newObj = findById(obj.getId())

        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);

    }

    public void delete(Long Id){

        findById(Id);

        try{
            this.userRepository.deleteById(Id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel exibir pois há entidade relacionada");
        }
    }

}
