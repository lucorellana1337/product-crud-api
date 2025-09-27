package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import model.Product;
import service.ProductService;

import java.io.*;
import java.net.URI;

public class ProductController implements HttpHandler {
    private ProductService service = new ProductService();
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();

        try {
            if ("GET".equalsIgnoreCase(method)) {
                if (query != null && query.startsWith("id=")) {
                    int id = Integer.parseInt(query.split("=")[1]);
                    Product p = service.getById(id);
                    respond(exchange, gson.toJson(p));
                } else {
                    respond(exchange, gson.toJson(service.getAll()));
                }
            } else if ("POST".equalsIgnoreCase(method)) {
                Product p = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Product.class);
                service.add(p);
                respond(exchange, "{\"status\":\"Producto agregado\"}");
            } else if ("PUT".equalsIgnoreCase(method)) {
                int id = Integer.parseInt(query.split("=")[1]);
                Product p = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Product.class);
                p.setId(id);
                service.update(p);
                respond(exchange, "{\"status\":\"Producto actualizado\"}");
            } else if ("DELETE".equalsIgnoreCase(method)) {
                int id = Integer.parseInt(query.split("=")[1]);
                service.delete(id);
                respond(exchange, "{\"status\":\"Producto eliminado\"}");
            } else {
                respond(exchange, "{\"error\":\"Método no soportado\"}", 405);
            }
        } catch (Exception e) {
            respond(exchange, "{\"error\":\"" + e.getMessage() + "\"}", 500);
        }
    }

    private void respond(HttpExchange exchange, String response) throws IOException {
        respond(exchange, response, 200);
    }

    private void respond(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
