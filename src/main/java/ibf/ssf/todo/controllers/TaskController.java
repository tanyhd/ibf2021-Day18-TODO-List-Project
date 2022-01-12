package ibf.ssf.todo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/task", produces = MediaType.TEXT_HTML_VALUE)
public class TaskController {

    private final Logger logger = Logger.getLogger(TaskController.class.getName());

    @PostMapping("save")
    public String save(@RequestBody MultiValueMap<String, String> form) {
        String contents = form.getFirst("contents");

        logger.log(Level.INFO, "to be saved: '%s'".formatted(contents));
        return "index";
    }
    
    @PostMapping
    public String addTask(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String taskName = form.getFirst("taskName");
        String contents = form.getFirst("contents");

        logger.log(Level.INFO, "contents: %s".formatted(contents));

        List<String> tasks = new LinkedList<>();
        if (!contents.equals("") || !contents.equals(null)) {
            //append new task to content
            contents = "%s|%s".formatted(contents, taskName);
            tasks = Arrays.asList(contents.split("\\|"));
        } else {
            contents = taskName;
            tasks.add(contents);
        }

        logger.log(Level.INFO, "taskName: %s".formatted(taskName));
        logger.log(Level.INFO, "tasks: %s".formatted(tasks));

        
        model.addAttribute("contents", contents);
        model.addAttribute("tasks", tasks);
        return "index";
    }

}
