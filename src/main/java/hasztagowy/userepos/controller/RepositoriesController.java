package hasztagowy.userepos.controller;

import hasztagowy.userepos.model.Repository;
import hasztagowy.userepos.repository.RepositoriesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
public class RepositoriesController {

    @Autowired
    private RepositoriesServiceImpl repositoriesServiceImpl;

    public RepositoriesController(RepositoriesServiceImpl repositoriesServiceImpl) {
        this.repositoriesServiceImpl = repositoriesServiceImpl;
    }

    @GetMapping("/getUserRepositories")
    public Flux<Repository> getUserRepository(@RequestParam String user,
                                               @RequestParam Optional<String> sort,
                                               @RequestParam Optional<String> orderby,
                                               @RequestParam Optional<String> type) {


        return repositoriesServiceImpl.getUserRepositories(user, sort, orderby, type);
    }
}
