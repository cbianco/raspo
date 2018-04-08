package it.cbmz.raspo.internal.kafka.message;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Message {

	public String type;
	public String message;
	private Map<String, String> properties;

	@JsonAnyGetter
	public Map<String, String> getProperties() {
		return properties;
	}

	@JsonAnySetter
	public void add(String key, String value) {
		properties.put(key, value);
	}

	@JsonCreator
	public Message(
		@JsonProperty("type") String type,
		@JsonProperty("message") String message) {
		this.type = type;
		this.message = message;
		this.properties = new HashMap<>();
	}

	@JsonIgnore
	public String getMessage(){
		return message;
	}

	@Override
	public String toString() {

		return "Message{" +
			"type='" + type + '\'' +
			", message='" + message + '\'' +
			", properties=" + properties +
			'}';
	}

	public static Message toMessage(String json) {
		try {
			return mapper.readValue(json, Message.class);
		} catch (IOException e) {
			throw new RuntimeException("Invalid JSON:" + json, e);
		}
	}

	public static String toJSON(Message message) {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public String toJSON() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static Message of(String type, String message) {
		return toMessage(
			String.format(
				"{\"type\":\"%s\",\"message\":\"%s\"}", type, message));
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper(){
		return mapper;
	}

}
