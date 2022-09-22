package hasztagowy.userepos.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

public class ValidateParams {

    public static String generateUrl(String uri, Optional<String> sort, Optional<String> orderby) {


        if (sort.isEmpty() || sort.get().isBlank() ) {
            sort = Optional.of("name");

        }

        if (orderby.isEmpty() || orderby.get().isBlank() || (!orderby.get().equals("desc") && !orderby.get().equals("asc")  )) {
            orderby = Optional.of("asc");
        }

        String url = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("sort", sort.get())
                .queryParam("direction", orderby.get())
                .queryParam("per_page", 100)
                .build().toString();

        return url;
    }
}
