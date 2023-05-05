package br.com.solutions.pocketmonsters.utils;

import lombok.Getter;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class QueryBuilder {
    private final String query;
    private final Map<String, Object> parameters;
    private static final String CLOSING_DELIMITER = ")";
    private static final String RETURNING_ALL = "returning *";
    private static final String PARAMETER_SEPARATOR = ", ";

    public QueryBuilder(String query, Map<String, Object> parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    public static <T> QueryBuilder insertQuery(T object) {
        return new InsertBuilder<T>(object)
                .insert();
    }

    public static <T> QueryBuilder insertAllQuery(List<T> objects) {
        return new InsertBuilder<T>(objects)
                .insertAll();
    }

    public static <T> QueryBuilder updateQuery(T object) {
        return new UpdateBuilder<T>(object)
                .update();
    }

    public void insertParameters(Query query) {
        Map<String, Object> parameters = getParameters();
        for(String key : parameters.keySet()) {
            if (!Objects.equals(key, "id")) {
                query.setParameter(key, parameters.get(key));
            }
        }
    }

    public void updateParameters(Query query) {
        getParameters().forEach((key, value) -> query.setParameter(key + 0, value));
    }

    private static String incrementId(String tableName, int count) {
        return String.format("(select coalesce(max(id) + %d, %d) from %s)", count, count, tableName);
    }

    private static String castToEnum(String parameter, Object column) {
        return String.format("cast(:%s as %s)", parameter, column);
    }

    private static String toPostgresPattern(String[] words) {
        return String.join("_", words).toLowerCase();
    }

    private static String toJavaPattern(String field) {
        String[] words = field.split("_");
        StringBuilder camelCaseName = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (Objects.equals(word, words[0])) {
                camelCaseName.append(word.substring(0, 1).toLowerCase());
                camelCaseName.append(word.substring(1).toLowerCase());
            } else {
                camelCaseName.append(word.substring(0, 1).toUpperCase());
                camelCaseName.append(word.substring(1).toLowerCase());
            }
        }
        return camelCaseName.toString();
    }

    private static <T> String getTableName(T object) {
        return toPostgresPattern(object.getClass().getSimpleName().split("(?=[A-Z])"));
    }

    private static Map<String, Object> mapParameters(ReflectedObject reflectedObject) {
        List<String> columns = new ArrayList<>(reflectedObject.getFieldNames());
        Map<String, Object> map = new HashMap<>();
        for (String column : columns) {
            Object object = reflectedObject.getFieldValue(column);
            if (Objects.nonNull(object) || Objects.equals(column, "id")) {
                map.put(toPostgresPattern(column.split("(?=[A-Z])")), object);
            }
        }

        return map;
    }

    private static List<String> toJavaPattern(List<String> columns) {
        return columns.stream().map(QueryBuilder::toJavaPattern).collect(Collectors.toList());
    }

    private static class InsertBuilder<T> {
        private final StringBuilder stringBuilder;
        private final T object;
        private final List<T> objects;
        private ReflectedObject reflectedObject;
        private final String tableName;
        private Map<String, Object> parameters;
        private List<String> columns;
        private int count = 0;
        private List<String> fields;
        private final Map<String, Object> queryParameters;

        InsertBuilder(T object) {
            this.object = object;
            this.reflectedObject = new ReflectedObject(this.object);
            this.tableName = getTableName(object);
            this.parameters = mapParameters(reflectedObject);
            this.columns = new ArrayList<>(parameters.keySet());
            this.fields = toJavaPattern(columns);
            this.stringBuilder = new StringBuilder(String.format("insert into %s ", tableName));
            this.objects = null;
            this.queryParameters = new HashMap<>();
        }

        InsertBuilder(List<T> objects) {
            this.objects = objects;
            this.object = objects.get(count);
            this.reflectedObject = new ReflectedObject(object);
            this.tableName = getTableName(object);
            this.parameters = mapParameters(reflectedObject);
            this.columns = new ArrayList<>(parameters.keySet());
            this.fields = toJavaPattern(columns);
            this.stringBuilder = new StringBuilder(String.format("insert into %s ", tableName));
            this.queryParameters = new HashMap<>();
        }

        QueryBuilder insertAll() {
            return new InsertBuilder<T>(objects)
                    .allParameters()
                    .allValues()
                    .returning()
                    .build();
        }

        QueryBuilder insert() {
            return new InsertBuilder<T>(object)
                    .allParameters()
                    .values()
                    .returning()
                    .build();
        }

        InsertBuilder<T> allParameters() {
            addParameters(new ArrayList<>(parameters.keySet()), "(");
            return this;
        }

        InsertBuilder<T> values() {
            addParameters(new ArrayList<>(mapNamedParameters().values()), "values (");
            return this;
        }

        InsertBuilder<T> allValues() {
            stringBuilder.append("values");
            for (T object : objects) {
                iterateObject(object);
                List<String> columns = new ArrayList<>(mapNamedParameters().values());
                addParameters(columns, "(");
                stringBuilder.append(PARAMETER_SEPARATOR);
                count++;
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            return this;
        }

        private void iterateObject(T object) {
            this.reflectedObject = new ReflectedObject(object);
            this.parameters = mapParameters(reflectedObject);
            this.columns = new ArrayList<>(parameters.keySet());
            this.fields = toJavaPattern(columns);
        }

        private void addParameters(List<String> columns, String openingDelimiter) {
            stringBuilder.append(openingDelimiter);
            for (int i = 0; i < columns.size(); i++) {
                stringBuilder.append(columns.get(i));
                if (i < columns.size() - 1) {
                    stringBuilder.append(PARAMETER_SEPARATOR);
                }
            }
            stringBuilder.append(CLOSING_DELIMITER);
        }



        private Map<String, String> mapNamedParameters() {
            Map<String, String> namedParameters = new HashMap<>();
            for (int i=0; i<columns.size(); i++) {
                String column = columns.get(i);
                String parameter = column + count;
                if (Objects.equals(column, "id")) {
                    namedParameters.put(column, incrementId(tableName, count + 1));
                } else if (!reflectedObject.getFieldType(fields.get(i)).isEnum()) {
                    namedParameters.put(column, ":"+ parameter);
                    queryParameters.put(parameter, reflectedObject.getFieldValue(fields.get(i)));
                } else {
                    namedParameters.put(column, castToEnum(parameter, column));
                    queryParameters.put(parameter, reflectedObject.getFieldValue(fields.get(i)));
                }
            }
            return namedParameters;
        }

        InsertBuilder<T> returning() {
            stringBuilder.append(RETURNING_ALL);
            return this;
        }

        QueryBuilder build() {
            return new QueryBuilder(stringBuilder.toString(), queryParameters);
        }
    }

    private static class UpdateBuilder<T> {
        private final StringBuilder stringBuilder;
        private final T object;
        private final ReflectedObject reflectedObject;
        private final Map<String, Object> parameters;
        private final List<String> columns;
        private final List<String> fields;

        UpdateBuilder(T object) {
            this.object = object;
            this.reflectedObject = new ReflectedObject(this.object);
            this.parameters = mapParameters(reflectedObject);
            this.columns = new ArrayList<>(parameters.keySet());
            this.fields = toJavaPattern(columns);
            this.stringBuilder = new StringBuilder(String.format("update %s ", getTableName(object)));
        }

        QueryBuilder update() {
            return new UpdateBuilder<T>(object)
                    .set()
                    .where()
                    .returning()
                    .build();
        }

        UpdateBuilder<T> set() {
            stringBuilder.append("set ");
            Map<String, String > namedParameters = mapNamedParameters();
            for (String key : namedParameters.keySet()) {
                stringBuilder
                        .append(key)
                        .append(" = ")
                        .append(namedParameters.get(key))
                        .append(PARAMETER_SEPARATOR);
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

            return this;
        }

        private Map<String, String> mapNamedParameters() {
            Map<String, String> namedParameters = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                String column = columns.get(i);
                String field = fields.get(i);
                if (!Objects.equals(column, "id")) {
                    if (!reflectedObject.getFieldType(field).isEnum()) {
                        namedParameters.put(column, String.format(":%s0", field));
                    } else {
                        namedParameters.put(column, castToEnum(column + 0, column));
                    }
                }
            }
            return namedParameters;
        }

        UpdateBuilder<T> where() {
            stringBuilder.append(" where id = :id0 ");
            return this;
        }

        UpdateBuilder<T> returning() {
            stringBuilder.append(RETURNING_ALL);
            return this;
        }
        
        QueryBuilder build() {
            return new QueryBuilder(stringBuilder.toString(), parameters);
        }
    }
}
