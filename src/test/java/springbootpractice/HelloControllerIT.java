package springbootpractice;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.SpringbootFirstApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootFirstApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {

	@LocalServerPort
	private String port;
	
	private URL url;
	
	@Autowired
	private TestRestTemplate template;
	
	@Before
	public void setUp() throws Exception{
		this.url=new URL("http://localhost:" + port + "/");
	}
	
	@Test
	public void hello(){
		ResponseEntity<String> response = template.getForEntity(url.toString(),
                String.class);
		System.out.println("=========="+response.getBody());
	}
}
