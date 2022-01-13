package ibf.ssf.todo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ibf.ssf.todo.services.*;

@Controller
@RequestMapping(path="/task", produces = MediaType.TEXT_HTML_VALUE)
public class TaskController {

    private final Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private TaskService taskSvc;

    @PostMapping("user")
    public String getUser(@RequestBody MultiValueMap<String, String> form, Model model) {
        String userName = form.getFirst("userName");

        logger.log(Level.INFO, "user name: %s".formatted(userName));

        List<String> userTask = new ArrayList<>();
        String contents;
        if(taskSvc.hasKey(userName)) {
            userTask = taskSvc.getStringList(userName);
            contents = taskSvc.getString(userName);

            //logger.log(Level.INFO, "user name: %s".formatted(userTask));
            //logger.log(Level.INFO, "user name: %s".formatted(contents));

            model.addAttribute("userName", userName);
            model.addAttribute("contents", contents);
            model.addAttribute("tasks", userTask);
        } else {
            model.addAttribute("userName", userName);
            model.addAttribute("contents", "");
            model.addAttribute("tasks", "");
        }
        return "task";
    }

    @PostMapping("save")
    public String save(@RequestBody MultiValueMap<String, String> form) {
        String contents = form.getFirst("contents");
        String userName = form.getFirst("userName");

        logger.log(Level.INFO, "to be saved: '%s'".formatted(contents));
        logger.log(Level.INFO, "User name: '%s'".formatted(userName));

        taskSvc.save(userName, contents);
        return "index";
    }
    
    @PostMapping
    public String addTask(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String taskName = form.getFirst("taskName");
        String contents = form.getFirst("contents");
        String userName = form.getFirst("userName");

        logger.log(Level.INFO, "contents: %s".formatted(contents));

        List<String> tasks = new LinkedList<>();
        if (contents.trim().length() > 0 && !contents.equals(null)) {
            //append new task to content with ';' as delimiter
            contents = contents + ";" + taskName;
            //splits the String task using the delimiter into a array list to be rendered later on html using thymeleaf
            tasks = Arrays.asList(contents.split(";"));
        } else {
            contents = taskName;
            tasks.add(contents);
        }

        logger.log(Level.INFO, "taskName: %s".formatted(taskName));
        logger.log(Level.INFO, "tasks: %s".formatted(tasks));

        model.addAttribute("userName", userName);
        model.addAttribute("contents", contents);
        model.addAttribute("tasks", tasks);
        return "task";
    }

}
