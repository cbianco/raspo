package it.cbmz.raspo.internal.kafka.message;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Message {

	public String type;
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
		@JsonProperty("type") String type) {
		this.type = type;
		this.properties = new HashMap<>();
	}

	@Override
	public String toString() {

		return "Message{" +
			"type='" + type + '\'' +
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

	public static Message of(String type) {
		return toMessage(String.format("{\"type\":\"%s\"}", type));
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper(){
		return mapper;
	}

}
