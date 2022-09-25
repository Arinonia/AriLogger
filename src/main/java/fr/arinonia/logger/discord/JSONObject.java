package fr.arinonia.logger.discord;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSONObject {
    private final HashMap<String, Object> map = new HashMap<>();

    void put(final String key, final Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        builder.append("{");

        int i = 0;
        for (final Map.Entry<String, Object> entry : entrySet) {
            final Object val = entry.getValue();
            builder.append(quote(entry.getKey())).append(":");

            if (val instanceof String) {
                builder.append(quote(String.valueOf(val)));
            } else if (val instanceof Integer) {
                builder.append(Integer.valueOf(String.valueOf(val)));
            } else if (val instanceof Boolean) {
                builder.append(val);
            } else if (val instanceof JSONObject) {
                builder.append(val.toString());
            } else if (val.getClass().isArray()) {
                builder.append("[");
                final int len = Array.getLength(val);
                for (int j = 0; j < len; j++) {
                    builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                }
                builder.append("]");
            }

            builder.append(++i == entrySet.size() ? "}" : ",");
        }

        return builder.toString();
    }

    private String quote(final String string) {
        return "\"" + string + "\"";
    }
}
