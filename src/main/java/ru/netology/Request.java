package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Request {
    private final String method;
    private final String path;
    private final String queryString;
    private final List<NameValuePair> queryParams;

    public Request(String method, String uri) {
        List<NameValuePair> queryParams1;
        this.method = method;

        // Разделяем URI на путь и query string
        int queryStart = uri.indexOf('?');
        if (queryStart >= 0) {
            this.path = uri.substring(0, queryStart);
            this.queryString = uri.substring(queryStart + 1);

            // Парсим параметры запроса с помощью URLEncodedUtils
            try {
                queryParams1 = URLEncodedUtils.parse(new URI("http://localhost/?" + queryString), StandardCharsets.UTF_8);
            } catch (URISyntaxException e) {
                // В случае некорректного URI используем пустой список параметров
                queryParams1 = Collections.emptyList();
            }
        } else {
            this.path = uri;
            this.queryString = null;
            queryParams1 = Collections.emptyList();
        }
        this.queryParams = queryParams1;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    // Возвращает значение параметра запроса по имени
    public String getQueryParam(String name) {
        for (NameValuePair param : queryParams) {
            if (param.getName().equals(name)) {
                return param.getValue();
            }
        }
        return null;
    }

    // Возвращает все параметры запроса
    public List<NameValuePair> getQueryParams() {
        return new ArrayList<>(queryParams);
    }
}


