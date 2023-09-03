package pl.kazanik.betterreadsdataloader;

import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.author.AuthorRepository;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.book.BookRepository;
import pl.kazanik.betterreadsdataloader.connection.DatastaxAstraProperties;
import pl.kazanik.betterreadsdataloader.parser.DataParser;
import pl.kazanik.betterreadsdataloader.persistance.DataLoader;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraProperties.class)
@EnableFeignClients
public class BetterreadsDataLoaderApplication {

    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Value("${datadump.location.authors}")
    private String authorsDumpLocation;
    
    @Value("${datadump.location.works}")
    private String worksDumpLocation;
    
	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}
    
    @PostConstruct
    public void loadData() {
        DataParser dataParser = new DataParser();
        
        List<AuthorEntity> authors = dataParser.parseAuthorsData(authorsDumpLocation);
        
        List<BookEntity> books = dataParser.parseBooksData(authors, worksDumpLocation);
        
        DataLoader dataLoader = new DataLoader(authorRepository, bookRepository);
        dataLoader.persistAuthors(authors);
        dataLoader.persistBooks(books);
    }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(
            DatastaxAstraProperties astraProperties) {
        
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
