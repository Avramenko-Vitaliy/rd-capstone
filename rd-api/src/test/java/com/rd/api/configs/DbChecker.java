package com.rd.api.configs;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import junit.framework.AssertionFailedError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CommonsLog
@RequiredArgsConstructor
public class DbChecker {
    private final JdbcTemplate template;

    public void checkDb(ExpectedData expected, String query, Object... params) {
        int i = 0;

        while(i < 2) {
            try {
                this.doCheckDb(expected, query, params);
                return;
            } catch (Throwable ex) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ++i;
            }
        }
        this.doCheckDb(expected, query, params);
    }

    public void doCheckDb(ExpectedData expected, String query, Object... params) {
        List<List<String>> actual = this.executeQuery(query, params);

        try {
            ReflectionAssert.assertReflectionEquals(expected.getData(), actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        } catch (AssertionFailedError ex) {
            StringBuilder sb = new StringBuilder("ExpectedData must be:\nnew ExpectedData()\n");
            for (List<String> strings : actual) {
                sb.append(String.format(".addRow(\"%s\")\n", Joiner.on("\", \"").join(strings)));
            }

            log.error(sb.toString().replaceAll("\"__NULL__\"", "null"));
            throw ex;
        }
    }

    private List<List<String>> executeQuery(String query, Object... params) {
        RowMapper<List<String>> rowMapper = (rs, rowNum) -> {
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            List<String> res = Lists.newArrayList();

            for(int i = 1; i <= columnCount; ++i) {
                String val = rs.getString(i);
                res.add(null == val ? "__NULL__" : val);
            }

            return res;
        };
        return this.template.query(query, params, rowMapper);
    }

    public void expectOne(String query, Object... params) {
        this.checkDb((new ExpectedData()).addRow("1"), query, params);
    }

    public static ExpectedData expectedData() {
        return new ExpectedData();
    }

    @Getter
    public static class ExpectedData {
        public static final String NULL = "__NULL__";
        private final List<List<String>> data = Lists.newArrayList();

        public ExpectedData addRow(String... values) {
            this.data.add(Lists.newArrayList(values));
            return this;
        }

    }
}
