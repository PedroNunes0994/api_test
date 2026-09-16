package api_teste.ds.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.ds.models.Task;
import api_teste.ds.models.User;
import api_teste.ds.repositories.TaskRepository;

@Service 
public class TaskService {

    @Autowired 
    private TaskRepository taskRepository;
    @Autowired 
    private UserService userService;
    
}
