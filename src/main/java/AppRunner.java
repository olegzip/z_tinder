import util.TinderServer;

public class AppRunner {
  public static void main(String[] args) throws Exception {
    TinderServer server = new TinderServer();
    server.start();
  }
}