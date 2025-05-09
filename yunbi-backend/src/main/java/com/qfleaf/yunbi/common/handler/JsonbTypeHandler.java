package com.qfleaf.yunbi.common.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 支持泛型的PostgreSQL JSONB类型处理器
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.OTHER)
public class JsonbTypeHandler<T> extends JacksonTypeHandler {

    private static final String JSONB = "jsonb";
    private static final String JSON = "json";

    private final Type type;

    public JsonbTypeHandler() {
        this(Object.class);
    }

    public JsonbTypeHandler(Class<T> type) {
        super(type);
        this.type = type;
    }

    public JsonbTypeHandler(TypeReference<T> typeReference) {
        this(typeReference.getType());
    }

    public JsonbTypeHandler(Type type) {
        super(determineRawType(type));
        this.type = type;
    }

    private static Class<?> determineRawType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            return Object.class;
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, JdbcType.OTHER.TYPE_CODE);
            return;
        }

        PGobject jsonObject = new PGobject();
        jsonObject.setType(JSONB);
        jsonObject.setValue(toJson(parameter));
        ps.setObject(i, jsonObject);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getObject(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getObject(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getObject(columnIndex));
    }

    private T convert(Object value) throws SQLException {
        if (value == null) {
            return null;
        }

        if (value instanceof PGobject pgObject) {
            if (!StringUtils.equalsAnyIgnoreCase(pgObject.getType(), JSONB, JSON)) {
                throw new SQLException("Unexpected PGobject type: " + pgObject.getType());
            }
            return parse(pgObject.getValue());
        }
        return parse(value.toString());
    }

    @Override
    public String toJson(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    @Override
    public T parse(String json) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructType(type);
        try {
            return getObjectMapper().readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSONB data", e);
        }
    }
}