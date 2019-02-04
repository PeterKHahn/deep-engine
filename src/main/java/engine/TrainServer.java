package engine;

import game.DynamicGameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TrainServer implements EngineListener, AutoCloseable{

    private ServerSocket serverSocket;
    private Socket connectionSocket;

    private Scanner scanner;
    private PrintWriter serverPrintOut;

    public TrainServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        connectionSocket = serverSocket.accept();

        InputStream inputToServer = connectionSocket.getInputStream();
        OutputStream outputFromServer = connectionSocket.getOutputStream();

        scanner = new Scanner(inputToServer, "UTF-8");
        serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, StandardCharsets.UTF_8), true);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
        }





    }

    @Override
    public void onUpdate(DynamicGameState gameState) {
        serverPrintOut.println("SOMETHING ABOUT THE GAME STATE");
    }

    @Override
    public void close() throws IOException {
        connectionSocket.close();

        serverSocket.close();
        scanner.close();
        serverPrintOut.close();
    }
}
