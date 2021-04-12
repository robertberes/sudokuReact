package sk.tuke.colorsudoku;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.colorsudoku.game.consoleui.ConsoleUI;
import sk.tuke.colorsudoku.game.core.Field;
import sk.tuke.colorsudoku.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.colorsudoku.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
 //       SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI(Field field) {
        return new ConsoleUI(field);
    }

    @Bean
    public Field field() {
        return new Field();
    }

    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJDBC();
        //return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        //return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService CommentService() {
        //return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
