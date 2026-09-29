package api_teste.ds.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import api_teste.ds.models.Task;
import api_teste.ds.services.TaskService;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Busca tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // Busca tarefas por ID do usuário (corrigido userid para userId na rota)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        List<Task> objs = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(objs);
    }

    // Criação de nova tarefa (trocado de @GetMapping para @PostMapping)
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) {
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Atualização de tarefa existente (removido o método duplicado com @PostMapping)
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    // Deleção de tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}