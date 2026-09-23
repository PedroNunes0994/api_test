package api_teste.ds.controllers;

import java.net.URI;//importa a classe URI para construir e manipuçar HTTP de novos recursos
import java.util.List;//importa a interface List para manipulara classe controller está localizada

import org.springframework.beans.factory.annotation.Autowired;//injeção automatica do spring
import org.springframework.http.ResponseEntity;//importa a classe para montar a resposta HTTP completa(status, headrs
import org.springframework.validation.annotation.Validated;//importa anotação para habilitar suporte a validação do controller
import org.springframework.web.bind.annotation.DeleteMapping;//mapeia requisições do tipo delete
import org.springframework.web.bind.annotation.GetMapping;//mapeia requisições do tipo GET
import org.springframework.web.bind.annotation.PathVariable;//mapeia variaveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PostMapping;//mapeia requisições do tipo POST
import org.springframework.web.bind.annotation.PutMapping;//mapeia requisições do tipo PUT
import org.springframework.web.bind.annotation.RequestBody;//converte objetos JSON em objetos JAVA
import org.springframework.web.bind.annotation.RequestMapping;//importa anotação para definir o caminho/rota base do controlador 
import org.springframework.web.bind.annotation.RestController;//importa anotação que define esta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;//importa utilitario para gerar a URI da requisição atual dinamicamente


import jakarta.validation.Valid;//importa a anotação para acionar a validação do corpo da requisição
import api_teste.ds.models.Task;//importa a entidade Task no pacote de modelos do projeto
import api_teste.ds.services.TaskService;//importa a caçasse de serviço TaskService do projeto



@RestController //Define a classe com um controlador RESTque retorna respostas em JSON
@RequestMapping ("/task")//Define /task como a rota base de todos do endpoint deste controlador 
@Validated //Habilita o suporte as validações dentro do controlador
public class TaskController { //Declaração de classe publica TaskController 

    @Autowired 
    private TaskService taskService;



    @GetMapping ("/{id}")//Mapeia requisições HTTP GET na rota "/task{id}"
    public ResponseEntity<Task> findById(@PathVariable Long id){//Busca tarefa especifica pelo seu ID
        Task obj = this.taskService.findById(id);//Chama a camada de serviço para buscar a tarefa pelo seu ID 
        return ResponseEntity.ok().body(obj);//Retorna HTTP 200(ok)
    } //Fim do método findById
    
    @GetMapping("/user/{userid}")
        public ResponseEntity<List<Task>> FindAllByUserId(@PathVariable Long userId){
            List<Task> objs = this.taskService.findAllByUserId(userId);
            return ResponseEntity.ok().body(objs);


        }

        @GetMapping 
        public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
            this.taskService.create(obj);
            URI url = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(url).build();
        }

        @PostMapping ("/{id}")
        public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
            obj.setId(id);
            this.taskService.update(obj);
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping ("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id){
            this.taskService.delete(id);
            return ResponseEntity.noContent().build();

        }




}
