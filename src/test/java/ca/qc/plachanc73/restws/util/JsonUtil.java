package ca.qc.plachanc73.restws.util;

import static ca.qc.plachanc73.restws.util.ExceptionUtil.getStringFromStackTrace;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	public static String serialiseObject(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonInString = null;
		try {
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			System.out.println(jsonInString);

		} catch (JsonProcessingException e) {
			LOGGER.debug(getStringFromStackTrace(e));
		}

		return jsonInString;
	}

	public static Object deserialiseJson(String json, Class<?> objetClasse) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		Object object = null;
		try {
			object = mapper.readValue(json, objetClasse);
		} catch (IOException e) {
			LOGGER.debug(getStringFromStackTrace(e));
		}

		return object;
	}
}