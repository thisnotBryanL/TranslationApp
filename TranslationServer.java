import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TranslationServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java TranslationServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter sockOut =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader sockIn = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine, outputLine;
            // Initiate conversation with client
            TranslationProtocol kkp = new TranslationProtocol();
            outputLine = kkp.processInput(null);
            sockOut.println(outputLine);
            while ((inputLine = sockIn.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                sockOut.println(outputLine);
                if (outputLine.equals("Bye.")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
