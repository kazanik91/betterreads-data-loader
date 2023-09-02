package pl.kazanik.betterreadsdataloader;

import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pl.kazanik.betterreadsdataloader.author.Author;
import pl.kazanik.betterreadsdataloader.author.AuthorRepository;

import pl.kazanik.betterreadsdataloader.connection.DatastaxAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraProperties.class)
public class BetterreadsDataLoaderApplication {

    @Autowired
    private AuthorRepository authorRepository;
    
	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}
    
    @PostConstruct
    public void start() {
        System.out.println("Application started");
        Author author = new Author();
        author.setId("id");
        author.setName("name");
        author.setPersonalName("personal");
        
        authorRepository.save(author);
    }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
