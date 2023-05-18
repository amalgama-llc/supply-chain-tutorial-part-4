package web.tutorial.backend.service;

import com.amalgamasimulation.randomdatamodel.RandomdatamodelFactory;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import web.tutorial.backend.dto.ScenarioDTO;

@Service
public class ScenarioMapper {

	public Scenario readFromDTO(ScenarioDTO scenarioDTO) {
		Scenario scenario = DatamodelFactory.eINSTANCE.createScenario();

		scenario.setName(scenarioDTO.name());
		scenario.setBeginDate(scenarioDTO.beginDate());
		scenario.setEndDate(scenarioDTO.endDate());

		var intervalBetweenRequests = RandomdatamodelFactory.eINSTANCE.createExponentialDistribution();
		intervalBetweenRequests.setMean(scenarioDTO.intervalBetweenRequestHrs());
		scenario.setIntervalBetweenRequestsHrs(intervalBetweenRequests);

		scenario.setMaxDeliveryTimeHrs(scenarioDTO.maxDeliveryTimeHrs());

		Map<String, Node> nodes = new HashMap<>();

		scenarioDTO.nodes().forEach(nodeDTO -> {
			String id = nodeDTO.id();
			var node = DatamodelFactory.eINSTANCE.createNode();
			node.setScenario(scenario);
			node.setId(id);
			node.setX(nodeDTO.x());
			node.setY(nodeDTO.y());
			nodes.put(id, node);
		});

		scenarioDTO.arcs().forEach(arcDTO -> {
			Node source = nodes.get(arcDTO.source());
			Node dest = nodes.get(arcDTO.dest());
			if (source.equals(dest)) {
				String message = "Arc source and destination should be different Nodes.\n";
				System.err.println(message);
				throw new RuntimeException(message);
			}
			var arc = DatamodelFactory.eINSTANCE.createArc();
			arc.setScenario(scenario);
			arc.setSource(source);
			arc.setDest(dest);
		});

		scenarioDTO.trucks().forEach(truckDTO -> {
			var truck = DatamodelFactory.eINSTANCE.createTruck();
			truck.setScenario(scenario);
			truck.setId(truckDTO.id());
			truck.setName(truckDTO.name());
			truck.setSpeed(truckDTO.speed());
			truck.setInitialNode(nodes.get(truckDTO.initialNode()));
		});

		scenarioDTO.warehouses().forEach(whDTO -> {
			var wh = DatamodelFactory.eINSTANCE.createWarehouse();
			wh.setScenario(scenario);
			wh.setId(whDTO.id());
			wh.setName(whDTO.name());
			wh.setNode(nodes.get(whDTO.node()));
		});

		scenarioDTO.stores().forEach(storeDTO -> {
			var store = DatamodelFactory.eINSTANCE.createStore();
			store.setScenario(scenario);
			store.setId(storeDTO.id());
			store.setName(storeDTO.name());
			store.setNode(nodes.get(storeDTO.node()));
		});

		return scenario;
	}
}
