package pl.kazanik.betterreadsdataloader;

import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.connection.DatastaxAstraProperties;
import pl.kazanik.betterreadsdataloader.parser.json.JsonParser;
import pl.kazanik.betterreadsdataloader.parser.json.factory.IJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.factory.impl.AuthorsJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.factory.impl.BooksJsonParserFactory;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraProperties.class)
@EnableFeignClients
public class BetterreadsDataLoaderApplication {

    
    @Value("${datadump.location.authors}")
    private String authorsDumpLocation;
    
    @Value("${datadump.location.works}")
    private String worksDumpLocation;
    
	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}
    
    @PostConstruct
    public void start() {
        System.out.println("###########################");
        System.out.println("Parsing authors...");
        System.out.println("###########################\n");
        IJsonParserFactory<AuthorEntity> authorsParserFactory = new AuthorsJsonParserFactory();
        JsonParser<AuthorEntity> authorsParser = authorsParserFactory.createJsonParser();
        List<AuthorEntity> authors = authorsParser.parse(authorsDumpLocation);
        
        System.out.println("\n###########################");
        System.out.println("Parsing books...");
        System.out.println("###########################");
        IJsonParserFactory<BookEntity> booksParserFactory = new BooksJsonParserFactory(authors);
        JsonParser<BookEntity> booksParser = booksParserFactory.createJsonParser();
        List<BookEntity> books = booksParser.parse(worksDumpLocation);
    }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(
            DatastaxAstraProperties astraProperties) {
        
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
