//Pacote onde está a classe de serviço no projeto
package api_teste.ds.services;

//importa List da Biblioteca paddrão do java para manipular coleções de objetos.
import java.util.List;
//importa Optional, usado para tratar valores que podem não estar presentes (evita NullExceptionPointer).
import java.util.Optional;


//importa a anotação do Spring para a injeção automática de dependecias
import org.springframework.beans.factory.annotation.Autowired;
//importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;
//importa a anotação para gerenciar transações no banco de dados(garante atomicidade na operação).
import org.springframework.transaction.annotation.Transactional;

//importa o models.Task
import api_teste.ds.models.Task;
//importa o models.User
import api_teste.ds.models.User;
//importa a interface do repositório responsável pelas operações no banco de dados
import api_teste.ds.repositories.TaskRepository;


//anotação que indica para o Spring que essa classe contém as regras de negocio
@Service 
public class TaskService {

    //injeta automaticamente a instancia do TaskRepository gerenciado pelo Spring
    @Autowired 
    private TaskRepository taskRepository;
    //injeta automaticamente a instancia do UserService para validar o usúario 
    @Autowired 
    private UserService userService;
    
    //metodo para buscar task apartir do ID
    public Task findById(long Id){
        //executa a busca no banco, retorna um optional contendo ou não a Task 
        Optional<Task> task = this.taskRepository.findById(Id);
   
        
        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada! Id:"+ Id + ",tipo:" + Task.class.getName()
        ));
    }

//metodo para buscar todas as tarefas vinculadas a um determinado usuario
    public List<Task> FindByUserId(Long UserId){

        //Chama o UserService para garantir que o usuario existe no banco(lança exceção se não existir)
        this.userService.findById(UserId);

        //executa a busca customizada no repositorio filtrando pelo id do usuario 
        List<Task> tasks = this.taskRepository.findByUserId(UserId);

        //retorna a lista de tarefas
        return tasks;
    }

        //garante que a criação ocorra dentro de uma transação de banco de dados(rollback automático se falhar)
        @Transactional 
        public Task create(Task obj){

            //valida se o usuario informado no objeto realmente existe no banco e recupera seus dados
            User user = this.userService.findById(obj.getUser().getId());

            //define o ID como null para garantir que o JPA realize uma inserção(INSERT) e não uma atualização
            obj.setId(id:null);

            //associa a entidade User completa e validada a tarefa
            obj.setUser(user);

            //salva a nva tarefa no banco de dados e atualiza 'obj' com o ID gerado
            obj = this.taskRepository.save(obj);
            
            //retorna a tarefa salva
           return obj;
        }

        //Garante que a atualização ocorra dentro de transação isolada no bancp
        @Transactional 
        public Task update(Task obj){
            //reaproveita o findById para veerificar se a tarefa a ser atualizada existe realmente
            Task newObj = findById(obj.getId());

            //Atualiza apenas o campo descricao do objeto persistido com o novo valor
            newObj.setDescription(obj.getDescription());

            //salva a alteração no banco de dados e retorna o objeto atualizado
            return this.taskRepository.save(newObj);
        }
    
        //metodo para deletar uma tarefa pelo id
    public void delete(Long Id){
        //verifica se a tarefa existe antes de tentar deletar
        findById(Id);

        try{
            //solicita a remoção da tarefa no banco de dados pelo ID
            this.taskRepository.deleteById(Id); 
        } catch (Exception e){  
            //captura exeções (como violações de chave estrangeira e lança uma mensagem amigável)
            throw new RuntimeException("Não é possivel excluir pois não há tarefas relacionadas");
        }    
    }
}
