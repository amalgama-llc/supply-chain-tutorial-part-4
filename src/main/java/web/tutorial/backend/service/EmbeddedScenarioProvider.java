package web.tutorial.backend.service;

import com.company.tutorial3.datamodel.Scenario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import web.tutorial.backend.dto.ScenarioDTO;

@Service
public class EmbeddedScenarioProvider {

	@Autowired
	private ScenarioMapper scenarioMapper;

	public List<Scenario> readAll() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			List<Scenario> result = new ArrayList<>();
			for (var scenarioFile : resolver.getResources("/scenarios/*.json")) {
				result.add(scenarioMapper.readFromDTO(objectMapper.readValue(scenarioFile.getFile(), ScenarioDTO.class)));
			}
			return result;
		} catch (IOException e) {
			return List.of();
		}
	}
}
