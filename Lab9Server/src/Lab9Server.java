
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class Lab9Server {
    private static ServerSocket serverSocket;

	public static void main(String[] args) throws Exception {

        int port = 4444;
        serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        while (true) {
			Socket client = serverSocket.accept();
			BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter w = new PrintWriter(client.getOutputStream(), true);
//			w.println("Welcome, type bye to close server");
			String line;
			do {
				line = r.readLine();
				if ( line != null && !(line = line.trim()).equals("")) {
					w.println(new ExpressionParser(line).getOutput());
				}

			} while ( line != null && !line.trim().equals("bye") );
			client.close();
        }
    }
}


