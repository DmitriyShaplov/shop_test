package ru.test.junior.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.test.junior.dto.StatRequestDto;
import ru.test.junior.dto.StatResponseDto;
import ru.test.junior.repository.StatRepository;

import java.io.File;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service("STAT")
@RequiredArgsConstructor
public class StatService implements ProcessingService {

    private final ObjectMapper objectMapper;
    private final StatRepository statRepository;

    @Override
    public void process(File input, File output) throws Exception {
        StatRequestDto requestStatDto = objectMapper.readValue(input, StatRequestDto.class);
        int daysCount = Period.between(requestStatDto.getStartDate(), (requestStatDto.getEndDate())).getDays() + 1;
        List<Object[]> statModels = statRepository.customerStat(requestStatDto.getStartDate(), requestStatDto.getEndDate());
        Map<List<Object>, List<StatResponseDto.CustomerResult.PurchaseResult>> collectedMap =
                statModels.stream().collect(
                        Collectors.groupingBy(
                                objects -> Arrays.asList(objects[0], objects[1]),
                                LinkedHashMap::new,
                                Collectors.mapping(v -> new StatResponseDto.CustomerResult.PurchaseResult((String) ((Object[]) v)[2], (Double) ((Object[]) v)[3]),
                                        Collectors.toList())
                        )
                );
        Set<StatResponseDto.CustomerResult> customerResults = new TreeSet<>(
                Comparator.comparingDouble(StatResponseDto.CustomerResult::getTotalExpenses).reversed());
        collectedMap.forEach((key, value) -> customerResults.add(
                new StatResponseDto.CustomerResult(
                        (String) key.get(1),
                        value,
                        value.stream().mapToDouble(pr -> pr.getExpenses()).sum())
        ));
        StatResponseDto statResponseDto = new StatResponseDto();
        statResponseDto.setCustomers(new ArrayList<>(customerResults));
        statResponseDto.setTotalDays(daysCount);
        statResponseDto.setType("stat");

        DoubleSummaryStatistics doubleSummaryStatistics = customerResults.stream()
                .mapToDouble(v -> v.getTotalExpenses()).summaryStatistics();
        statResponseDto.setTotalExpenses(doubleSummaryStatistics.getSum());
        statResponseDto.setAvgExpenses(doubleSummaryStatistics.getAverage());

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(output, statResponseDto);
    }
}
