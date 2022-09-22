package hasztagowy.userepos.service;

import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.RepositoryModel;
import hasztagowy.userepos.utils.ValidateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.util.Optional;

@Service
public class RepositoriesServiceImpl {

    private final WebClient.Builder webClientBuilder;
    private int page;

    @Autowired
    public RepositoriesServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<RepositoryModel> getUserRepositories(String name, Optional<String> sort, Optional<String> orderby, Optional<String> type) {

        String uri = "https://api.github.com/users/" + name + "/repos";

        String url = ValidateParams.generateUrl(uri, sort, orderby);

        page = 1;

        Flux<RepositoryModel> repositoryFlux = getData(url);

        //if type is not in (true, false) ignore
        if (type.isPresent() && (type.get().equals("true") || type.get().equals("false"))) {
            return repositoryFlux.map(x -> {
                x.setUpdatedRecently(RepositoryModel.checkLastUpdateDate(x.getUpdatedAt()));
                return x;
            }).filter(repositoryModel -> repositoryModel.getUpdatedRecently() == Boolean.valueOf(type.get()));
        } else {
            return repositoryFlux.map(x -> {
                x.setUpdatedRecently(RepositoryModel.checkLastUpdateDate(x.getUpdatedAt()));
                return x;
            });
        }
    }

    private Flux<RepositoryModel> getData(String url) {
        return getNextPage(url, page)
                .expand(fluxResponseEntity -> {
                    fluxResponseEntity.getHeaders().get("Link"); //get header where GitHub api puts information about next page
                    if (fluxResponseEntity.getHeaders().get("Link") == null || !(fluxResponseEntity.getHeaders().get("Link").toString().contains(("rel=" + "\"next\"")))) {
                        return Mono.empty(); //if header have no value with "rel="next" return empty mono
                    }
                    page++;
                    System.out.println(page);
                    return getNextPage(url, page);
                })
                .flatMap(HttpEntity::getBody); //to return flux
    }

    private Mono<ResponseEntity<Flux<RepositoryModel>>> getNextPage(String url, int page) {

        return webClientBuilder.build()
                .get()
                .uri(url + "&page=" + page)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.INTERNAL_SERVER_ERROR.value(), clientResponse -> Mono.error(new ServiceUnavailableException("Service is not available"))) //api error
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.FORBIDDEN.value(), clientResponse -> Mono.error(new ServiceUnavailableException("Forbidden"))) //when user used up the query limit
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new UserNotFoundException("User not found"))) //not found user
                .toEntityFlux(RepositoryModel.class);
    }
}
