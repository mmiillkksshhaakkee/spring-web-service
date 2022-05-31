package demo;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


@Entity
class Message {
	@Id
	private String id;
	private String content;

	public Message(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public Message(String content){
		this.id = UUID.randomUUID().toString();
		this.content = content;
	}

	public Message(){
		this.id = UUID.randomUUID().toString();
		this.content = "";

	}

	public String getId(){
		return id;
	}

	public String getContent(){
		return content;
	}

	public void setId(String id){
		this.id = id;
	}
	public void setContent(String content){
		this.content = content;
	}
}


	@RestController
@RequestMapping("/msgs")
class RestApiController {

	private final MsgRepository msgRepository;

	public RestApiController(MsgRepository msgRepository) {

		this.msgRepository = msgRepository;

		this.msgRepository.saveAll(List.of(
				new Message("Message 1"),
				new Message("Message 2"),
				new Message("Message 3")
		));

	}

	/*@RequestMapping(value = "/msgs", method = RequestMethod.GET)
	Iterable<Message> getMessages(){
		return msgs;
	}*/

	@GetMapping
	Iterable<Message> getMessages(){
		return msgRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Message> getMessageById(@PathVariable String id){
		return msgRepository.findById(id);
	}

	@PostMapping
	Message postMessage(@RequestBody Message msg){
		return msgRepository.save(msg);
	}

	@PutMapping("/{id}")
	/*Message putMessage(@PathVariable String id, @RequestBody Message msg){
		int msgInd = -1;
		for (Message m: msgs){
			if(m.getId().equals(id)){
				msgInd = msgs.indexOf(m);
				msgs.set(msgInd, msg);
			}
		}
		return (msgInd == -1) ? postMessage(msg) : msg;
	}*/

	ResponseEntity<Message> putMessage(@PathVariable String id, @RequestBody Message msg) {

		return (!msgRepository.existById(id)) ? new ResponseEntity<>(msgRepository.save(msg), HttpStatus.CREATED) : new ResponseEntity<>(msgRepository.save(msg), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteMessage(@PathVariable String id){
		msgRepository.deleteById(id);
	}

}