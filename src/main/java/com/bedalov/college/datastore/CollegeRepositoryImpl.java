package com.bedalov.college.datastore;

import com.bedalov.college.config.CollegeRepositoryConfiguration;
import com.bedalov.college.config.Datastore;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollegeRepositoryImpl implements CollegeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollegeRepositoryImpl.class);
    private final Map<String, CollegeEntity> map = new HashMap<>();

    @Autowired
    public CollegeRepositoryImpl(CollegeRepositoryConfiguration configuration) throws IOException {
        Iterable<CSVRecord> records = getRecords(configuration.getDatastores());
        for (CSVRecord record : records) {
            CollegeEntity collegeEntity = createEntity(record);
            if (StringUtils.isEmpty(collegeEntity.getCollegeName())) {
                handleEmpty();
                continue;
            }
            if (map.putIfAbsent(collegeEntity.getCollegeName(), collegeEntity) != null) {
                handleDuplicate(collegeEntity);
            }
        }
    }

    private Iterable<CSVRecord> getRecords(List<Datastore> datastores) throws IOException {
        List<CSVRecord> results = new ArrayList<>();
        for (Datastore datastore : datastores) {
            getRecords(datastore).forEach(results::add);
        }
        return results;
    }

    private CSVParser getRecords(Datastore datastore) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(
                this.getClass().getResourceAsStream(datastore.getFile()), Charset.forName(datastore.getCharset()));
        return CSVFormat.EXCEL.withFirstRecordAsHeader().parse(inputStreamReader);
    }

    private CollegeEntity createEntity(CSVRecord record) {
        return new CollegeEntity(
                record.get(0),
                handleDouble(record.get(1)),
                handleDouble(record.get(2)),
                handleDouble(record.get(3)));
    }

    private void handleDuplicate(CollegeEntity obj) {
        LOGGER.info("Found duplicate college: {}", obj.getCollegeName());
    }

    private void handleEmpty() {
        LOGGER.info("Found college without name; skipping");
    }

    private double handleDouble(String input) {
        if (StringUtils.isEmpty(input)) {
            return 0.0;
        }
        return Double.parseDouble(input);
    }

    @Override
    public CollegeEntity getEntity(String key) {
        return map.get(key);
    }
}
