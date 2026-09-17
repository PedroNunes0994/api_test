//declara o caminho onde a classe dentro do código 
package api_teste.ds.services;

//importa Optional,usado para tratar valores que podem não estar presentes (evita NulleceptionPointer)
import java.util.Optional;

//importa a anotação do Spring para a injeção automátca de dependencias
import org.springframework.beans.factory.annotation.Autowired;
//importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;
//importa a anotação para gerenciar transções no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//importa o models.Task
import api_teste.ds.models.Task;
//importa a interface do repositorio responsavel pelas operações no bvanco de dados
import api_teste.ds.repositories.TaskRepository;
//importa a interface do repositorio responsavel pelas operações no banco de dados
import api_teste.ds.repositories.UserRepository;

@Service 
public class UserService {
    
    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private TaskRepository taskRepository;

    public User findById(Long Id){

        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(()-> new RuntimeException(
            "Usuario não encontrado!" + Id + ", tipo:" + User.class.getName()

        ));
    }

@Transactional

public User create(User obj){

    obj.setId(null);

    obj.this.userRepository.save(obj);

    this.taskRepository.saveAll(obj.getClass());

    return obj;
}


@Transactional 

    public User update(User obj){
        
        User newObj = findById(obj.getId());

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
