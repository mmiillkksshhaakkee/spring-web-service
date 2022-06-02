package demo;

import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@SpringBootApplication
public class WebProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebProjectApplication.class, args);
	}
}

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
class ResultDetails {

	private Map<String, Object> details = new HashMap<String, Object>();

	public ResultDetails() {};
	public ResultDetails(String field, String def){
		this.details.put(field, def);
	}
	public ResultDetails(String field, Integer def){
		this.details.put(field, def);
	}

	public ResultDetails(String field, Map<String, String> def){
		this.details.put(field, def);
	}

	public Map<String, Object> getDetails(){
		return details;
	}

	public void setDetails(String field, Object def){
		this.details.put(field, def);
	}
	/* String-String, String-Int, String-Map<String,String> */
}
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
class Response {
	private String request_id;
	private String reference;
	private Map<String, ResultDetails> results = new HashMap<String, ResultDetails>();

	private Map<String, Object> result_info = new HashMap<String, Object>();

	public Response(){};

	public String getRequestId() {
		return request_id;
	}

	public String getReference() {
		return reference;
	}

	public Map<String, ResultDetails> getResults() {
		return results;
	}

	public Map<String, Object> getResultInfo() {
		return result_info;

	}

	public void setRequestId(String request_id){
		this.request_id = request_id;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public void setResultInfo(Map<String, Object> result_info){
		this.result_info = result_info;
	}
}
	@RestController
@RequestMapping("/records")
class RestApiController {
	private List<Response> records = new ArrayList<>();


	@GetMapping
	Iterable<Response> getResponses(){
		return records;
	}

	@GetMapping("/{id}")
	Optional<Response> getResponseByRequestId(@PathVariable String id){
		for (Response r : records){
			if(r.getRequestId().equals(id)){
				return Optional.of(r);
			}
		}
		return Optional.empty();
	}

	@PostMapping
	Response postResponse(@RequestBody Response msg){
		records.add(msg);
		return msg;
	}

	@DeleteMapping("/{id}")
	void deleteResponse(@PathVariable String id){
		records.removeIf(r -> r.getRequestId().equals(id));
	}

}