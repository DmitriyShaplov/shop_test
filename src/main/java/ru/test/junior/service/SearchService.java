package ru.test.junior.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.test.junior.config.AppConfig;
import ru.test.junior.dto.RequestSearchCriteria;
import ru.test.junior.dto.SearchResultDto;
import ru.test.junior.dto.criteria.Criteria;
import ru.test.junior.enums.Operation;
import ru.test.junior.model.Customer;
import ru.test.junior.repository.SearchRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service("SEARCH")
public class SearchService implements ProcessingService {

    @Autowired
    private AppConfig.Properties properties;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SearchRepository searchRepository;

    @Override
    public void process(File input, File output) throws Exception {
        RequestSearchCriteria requestSearchCriteria = objectMapper.readValue(input, RequestSearchCriteria.class);
        SearchResultDto searchResultDto = new SearchResultDto();
        Map<Criteria, List<String>> values = properties.getValues();

        searchResultDto.setType(Operation.SEARCH.name().toLowerCase(Locale.ROOT));
        List<SearchResultDto.Result> results = new ArrayList<>();

        List<Map<String, String>> criterias = requestSearchCriteria.getCriterias();
        for (Map<String, String> criteria : criterias) {
            List<Customer> customers;
            if (criteria.keySet().containsAll(values.get(Criteria.LAST_NAME))) {
                customers = searchRepository.findCustomerByLastName(criteria.get("lastName"));
            } else if (criteria.keySet().containsAll(values.get(Criteria.PRODUCT))) {
                customers = searchRepository.findCustomersByPurchaseCount(
                        criteria.get("productName"), Long.parseLong(criteria.get("minTimes")));
            } else if (criteria.keySet().containsAll(values.get(Criteria.EXPENSE))) {
                customers = searchRepository.findByExpense(
                        Integer.parseInt(criteria.get("minExpenses")),
                        Integer.parseInt(criteria.get("maxExpenses"))
                );
            } else if (criteria.keySet().containsAll(values.get(Criteria.BAD_CUSTOMER))) {
                customers = searchRepository.findBadass(PageRequest.of(0, Integer.parseInt(criteria.get("badCustomers"))));
            } else {
                throw new RuntimeException("Bad input file");
            }
            SearchResultDto.Result result = new SearchResultDto.Result();
            result.setCriteria(criteria);
            List<SearchResultDto.Result.CustomerResult> customerResult = modelMapper.map(customers,
                    new TypeToken<List<SearchResultDto.Result.CustomerResult>>() {
                    }.getType());
            result.setResults(customerResult);
            results.add(result);
        }
        searchResultDto.setResults(results);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(output, searchResultDto);
    }
}
