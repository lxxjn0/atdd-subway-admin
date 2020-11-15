package wooteco.subway.admin.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.domain.LineStation;
import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.repository.LineRepository;
import wooteco.subway.admin.repository.StationRepository;

@Service
public class LineService {
	private LineRepository lineRepository;
	private StationRepository stationRepository;

	public LineService(LineRepository lineRepository, StationRepository stationRepository) {
		this.lineRepository = lineRepository;
		this.stationRepository = stationRepository;
	}

	public Line save(Line line) {
		return lineRepository.save(line);
	}

	public Line showLine(Long id) {
		return lineRepository.findById(id).orElseThrow(RuntimeException::new);
	}

	public List<Line> showLines() {
		return lineRepository.findAll();
	}

	public void updateLine(Long id, Line line) {
		Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
		persistLine.update(line);
		lineRepository.save(persistLine);
	}

	public void deleteLineById(Long id) {
		lineRepository.deleteById(id);
	}

	public void addLineStation(Long id, LineStationCreateRequest request) {
		Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
		persistLine.addLineStation(request.toLineStation());
		lineRepository.save(persistLine);
	}

	public void removeLineStation(Long lineId, Long stationId) {
		Line persistLine = lineRepository.findById(lineId).orElseThrow(RuntimeException::new);
		persistLine.removeLineStationById(stationId);
		lineRepository.save(persistLine);
	}

	public LineResponse findLineWithStationsById(Long id) {
		Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
		Set<Station> stations = stationRepository.findAllById(persistLine.getLineStationsId());

		return LineResponse.of(persistLine, stations);
	}
}
