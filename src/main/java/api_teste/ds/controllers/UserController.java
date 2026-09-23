package api_teste.ds.controllers;

//importa a classe URI para construir e manipular HTTP de novos recursos
import java.net.URI;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;//injeção automatica do spring
import org.springframework.http.ResponseEntity;//immporta a classe para montar a resposta HTTP completa(status, headrs
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

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;


@RestController //define a classe como um controlador REST que retorna respostas en JSON
@RequestMapping ("/user")//Define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated //ativa a verificação de validações nos parametros recebidos no controller 

public class UserController {

    @Autowired 
    private UserService userService;

    @GetMapping ("/{id}") //Mapeia requisições HTTP GET na rota "/user{id}"
    public ResponseEntity<User> findById(@PathVariable Long Id){ // metodo para buscar usuario por id capturado da URL
        User obj=this.userService.findById(Id);// Invoca a busca do usuario através do ID recebido 
        return ResponseEntity.ok().body(obj); //retorna código HTTP 200(ok) com o objeto user no corpo da resposta
    }//fim do método findById

@PostMapping //Mapeia requisições HTTP POST na rota base"/user"(criação de novo usuário)
public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj){ //valida regra de CreateUser e desserializa o corpo JSON
    this.userService.create(obj); // chama a camada de serviço para persistir o novo usuario no banco de dados.
    URI url = ServletUriComponentsBuilder.fromCurrentRequest()//Obtém a rota da requisição atual 
        .path("/{id}").buildAndExpand(obj.getId()).toUri(); // adiciona o Id do usuario gerado no final do caminho da URI
        return ResponseEntity.created(url).build();//Retorna código HTTP 201 (Created) contendo a URL no cabeçalho location
}

@PutMapping ("/{id}") // Mapeia requisições HTTP PUT na rota base "/user/{id}" (atualização do usuário)
public ResponseEntity<Void> update(@Validated(UpdateUser.class)@RequestBody User obj, @PathVariable Long id){ // aplica a regra de updateUser e recebe ID e JSON
    obj.setId(id);//Garante que o ID do objeto a ser atualizado correponsde ao ID informado no parametro da URL
    this.userService.update(obj);//Executa a atualização da senha do usuario no banco de dados
    return ResponseEntity.noContent().build();//Retorno código HTTP 204(No content)indicando sucesso


}

@DeleteMapping ("/{id}")//Mapeia requisições HTTP DELETE na rota "/user/{id}" (exclusão de usuario)
public ResponseEntity<Void> delete(@PathVariable Long id){ // Captura o Id da URL a ser deletado
    this.userService.delete(id); //invoca um metodo de deleção do serviço
    return ResponseEntity.noContent().build();//Retorna código HTTP 204 (no content confirmando a exclusão)

    }
}