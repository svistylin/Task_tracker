package com.tracker.task.tasktracker.util;

import com.tracker.task.tasktracker.dto.RoleDto;
import com.tracker.task.tasktracker.entity.Role;
import com.tracker.task.tasktracker.entity.Status;
import com.tracker.task.tasktracker.entity.Task;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.TaskRepository;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.service.RoleService;
import com.tracker.task.tasktracker.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class RandomGeneratorDbService {

    private final RoleService roleService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public RandomGeneratorDbService(RoleService roleService,
                                    UserService userService,
                                    BCryptPasswordEncoder encoder,
                                    TaskRepository taskRepository, UserRepository userRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.encoder = encoder;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void generateDb() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleService.addRole(roleUser.toDto());
        RoleDto roleAdmin = new RoleDto();
        roleAdmin.setName("ROLE_ADMIN");
        roleService.addRole(roleAdmin);
        generateUsers();
        generateTasks();
    }

    private void generateTasks() {
        for (int i = 0; i < 300; i++) {
            Task task = new Task();
            task.setTitle(RandomString.make());
            task.setDescription(RandomString.make());
            setRandomStatusForTask(task);
            long makerId = (long) (Math.random() * 51) + 1;
            Task saved = taskRepository.save(task);
            User user = userRepository.findByIdAndDeletedFalse(makerId).orElseThrow(RuntimeException::new);
            if (user.getTasks() == null) {
                user.setTasks(new ArrayList<>());
            }
            task.setMaker(user);
            user.getTasks().add(saved);
            userRepository.save(user);
            Task savedTask = taskRepository.save(task);
            savedTask.setMaker(user);
            taskRepository.save(task);
        }
    }

    private void setRandomStatusForTask(Task task) {
        int status = (int) (Math.random() * 3) + 1;
        switch (status) {
            case 1:
                task.setStatus(Status.In_Progress);
                break;
            case 2:
                task.setStatus(Status.View);
                break;
            case 3:
                task.setStatus(Status.Done);
                break;
        }
    }

    private void generateUsers() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            String lastName = RandomString.make();
            String firstName = RandomString.make();
            String password = RandomString.make();
            String email = RandomString.make();
            user.setEmail(email);
            user.setPassword(encoder.encode(password));
            user.setLastName(lastName);
            user.setFirstName(firstName);
            userService.save(user.toDto(), "ROLE_ADMIN");
        }
    }
}
