package pl.kazanik.betterreadsdataloader;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.json.JSONObject;
import pl.kazanik.betterreadsdataloader.author.Author;
import pl.kazanik.betterreadsdataloader.author.AuthorRepository;
import pl.kazanik.betterreadsdataloader.connection.DatastaxAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraProperties.class)
public class BetterreadsDataLoaderApplication {

    @Autowired
    private AuthorRepository authorRepository;
    
    @Value("${datadump.location.authors}")
    private String authorsDumpLocation;
    
    @Value("${datadump.location.works}")
    private String worksDumpLocation;
    
	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}
    
    private void initAuthors() {
        Path authorsPath = Paths.get(authorsDumpLocation);
        try (Stream<String> lines = Files.lines(authorsPath)) {
            lines.forEach(line -> {
                try {
                    // read and parse line
                    String json = line.substring(line.indexOf("{"));
                    JSONObject jsonObject = new JSONObject(json);

                    // construct Author object
                    Author author = new Author();
                    author.setName(jsonObject.optString("name"));
                    author.setPersonalName(jsonObject.optString("personal_name"));
                    String key = jsonObject.optString("key");
                    author.setId(key.replace("/authors/", ""));

                    // persist 
                    authorRepository.save(author);
                    
                    System.out.println("Author " + author.getName() + " saved");
                }
                catch (JSONException jsex) {
                    jsex.printStackTrace();
                }
            });
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void initWorks() {}
    
    @PostConstruct
    public void start() {
        initAuthors();
        initWorks();
    }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
