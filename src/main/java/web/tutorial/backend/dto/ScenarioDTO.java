package web.tutorial.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScenarioDTO(
													String name,
													LocalDateTime beginDate,
													LocalDateTime endDate,
													double intervalBetweenRequestHrs,
													double maxDeliveryTimeHrs,
													List<TruckDTO> trucks,
													List<NodeDTO> nodes,
													List<ArcDTO> arcs,
													List<WarehouseDTO> warehouses,
													List<StoreDTO> stores
													) {}
