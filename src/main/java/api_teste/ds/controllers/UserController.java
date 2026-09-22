package api_teste.ds.controllers;

//importa a classe URI para construir e manipular HTTP de novos recursos
import java.net.URI;

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
    public ResponseEntity<User> findyById(@PathVariable Long Id){ // metodo para buscar usuario por id capturado da URL
        User obj=this.userService.findyById(Id);// Invoca a busca do usuario através do ID recebido 
        return ResponseEntity.ok().body(obj); //retorna código HTTP 200(ok) com o objeto user no corpo da resposta
    }//fim do método findById

@PostMapping 
public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj){
    this.userService.create(obj);
    URI url = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();
}




}
