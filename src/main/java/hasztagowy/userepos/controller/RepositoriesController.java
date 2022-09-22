package hasztagowy.userepos.controller;

import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.RepositoryModel;
import hasztagowy.userepos.service.RepositoriesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/v1")
public class RepositoriesController {

    @Autowired
    private RepositoriesServiceImpl repositoriesServiceImpl;

    @GetMapping("/getUserRepositories")
    public Flux<RepositoryModel> getUserRepository(@RequestParam String user,
                                                   @RequestParam Optional<String> sort,
                                                   @RequestParam Optional<String> orderby,
                                                   @RequestParam Optional<String> type)  throws UserNotFoundException {

        if(user.isEmpty() || user.isBlank()){
            throw new UserNotFoundException("user cannot be null or empty");
        }
        return repositoriesServiceImpl.getUserRepositories(user, sort, orderby, type);
    }
}
