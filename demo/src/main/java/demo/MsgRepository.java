package demo;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

public interface MsgRepository extends CrudRepository<Message, String> {};