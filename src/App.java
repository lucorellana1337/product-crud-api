import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import db.Database;
import controller.ProductController;

public class App {
    public static void main(String[] args) throws Exception {
        Database.init(); // Inicializa la BD y crea tablas si no existen

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/productos", new ProductController());
        server.setExecutor(null);

        System.out.println("Servidor iniciado en http://localhost:8000");
        server.start();
    }
}
