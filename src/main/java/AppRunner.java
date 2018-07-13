import util.TinderServer;

public class AppRunner {
  public static void main(String[] args) throws Exception {

    String port = null;
    port = args.length > 0 ? args[0] : "8081";

    TinderServer server = new TinderServer();
    server.start(port);
  }
}