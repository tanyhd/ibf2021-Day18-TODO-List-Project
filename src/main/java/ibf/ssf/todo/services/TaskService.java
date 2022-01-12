package ibf.ssf.todo.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.ssf.todo.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepo;

    public boolean hasKey(String key) {
        Optional<String> opt = taskRepo.get(key);
        return opt.isPresent();
    }

    public List<String> get(String key) {
        Optional<String> opt = taskRepo.get(key);
        List<String> list = new LinkedList<>();
        if(opt.isEmpty()) {
            return list;
        } else {
            for (String t : opt.get().split("\\|")) {
                list.add(t);
            }
        } return list;
    }
}
