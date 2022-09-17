package hasztagowy.userepos.repository;

import hasztagowy.userepos.exceptions.UserNotFoundException;
import hasztagowy.userepos.model.Repository;
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
    private String uri;
    private int page=1;

    @Autowired
    public RepositoriesServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<Repository> getUserRepositories(String name, Optional<String> sort, Optional<String>  orderby, Optional<String> type){

        uri="https://api.github.com/users/"+name+"/repos?sort=updated&direction=desc&per_page=100";

        Flux<Repository> repositoryFlux=getData(uri);


        return repositoryFlux.map(x->{
            x.setUpdatedRecently(Repository.checkLastUpdateDate(x.getUpdatedAt()));
            return x;
        });
    }

    private Flux<Repository> getData(String url){
        return getNextPage(url,page)
                .expand(fluxResponseEntity -> {
                    fluxResponseEntity.getHeaders().get("Link");
                    if(fluxResponseEntity.getHeaders().get("Link")==null ||!(fluxResponseEntity.getHeaders().get("Link").toString().contains(("rel="+"\"next\"")))){
                        return Mono.empty();
                    }
                    page++;
                    System.out.println(page);
                    return getNextPage(url,page);
                })
                .flatMap(HttpEntity::getBody);
    }
    private Mono<ResponseEntity<Flux<Repository>>> getNextPage(String url, int page){

        return webClientBuilder.build()
                .get()
                .uri(url+"&page="+page)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value()== HttpStatus.INTERNAL_SERVER_ERROR.value(), clientResponse -> Mono.error(new ServiceUnavailableException("Service is not available")))
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->Mono.error(new UserNotFoundException("User not found")))
                .toEntityFlux(Repository.class);

    }
}
