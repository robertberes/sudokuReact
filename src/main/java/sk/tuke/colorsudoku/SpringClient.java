package sk.tuke.colorsudoku;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.colorsudoku.game.consoleui.ConsoleUI;
import sk.tuke.colorsudoku.game.core.Field;
import sk.tuke.colorsudoku.service.*;

@SpringBootApplication
@Configuration
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
//        pattern = "sk.tuke.colorsudoku.server.*"))
public class SpringClient {
    public static void main(String[] args) {
//        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
        SpringApplication.run(SpringClient.class);
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
        return new Field(99);
    }

    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJDBC();
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService CommentService() {
        return new CommentServiceJPA();
    }

}
