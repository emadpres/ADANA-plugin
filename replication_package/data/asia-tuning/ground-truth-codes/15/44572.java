@WebService
public class EchoService {

    public String echoHello(String name) {
        return "Hello " + name;
    }

};
EchoService service = new EchoService();    
Endpoint.publish("http://localhost:2000/echo", service);;
