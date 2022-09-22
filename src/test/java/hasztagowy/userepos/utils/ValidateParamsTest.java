package hasztagowy.userepos.utils;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class ValidateParamsTest {

    @Test
    void generateUrlWithWrongOrderByParam() {
        String uri = "https://api.github.com/users/hasztagowy/repos";
        Optional<String> sort = Optional.of("da");
        Optional<String> orderby = Optional.of("das");

        assert (ValidateParams.generateUrl(uri, sort, orderby)).equals("https://api.github.com/users/hasztagowy/repos?sort=da&direction=asc&per_page=100");
    }

    @Test
    void generateUrlWithDESCParam() {
        String uri = "https://api.github.com/users/hasztagowy/repos";
        Optional<String> sort = Optional.of("da");
        Optional<String> orderby = Optional.of("desc");

        assert (ValidateParams.generateUrl(uri, sort, orderby)).equals("https://api.github.com/users/hasztagowy/repos?sort=da&direction=desc&per_page=100");
    }

    @Test
    void generateUrlWithASCParam() {
        String uri = "https://api.github.com/users/hasztagowy/repos";
        Optional<String> sort = Optional.of("da");
        Optional<String> orderby = Optional.of("asc");

        assert (ValidateParams.generateUrl(uri, sort, orderby)).equals("https://api.github.com/users/hasztagowy/repos?sort=da&direction=asc&per_page=100");
    }

    @Test
    void generateUrlWithNoParams() {
        String uri = "https://api.github.com/users/hasztagowy/repos";
        Optional<String> sort = Optional.empty();
        Optional<String> orderby = Optional.empty();

        assert (ValidateParams.generateUrl(uri, sort, orderby)).equals("https://api.github.com/users/hasztagowy/repos?sort=name&direction=asc&per_page=100");
    }

    @Test
    void generateUrlWithNoOrderByParam() {
        String uri = "https://api.github.com/users/hasztagowy/repos";
        Optional<String> sort = Optional.of("da");
        Optional<String> orderby = Optional.empty();

        assert (ValidateParams.generateUrl(uri, sort, orderby)).equals("https://api.github.com/users/hasztagowy/repos?sort=da&direction=asc&per_page=100");
    }
}