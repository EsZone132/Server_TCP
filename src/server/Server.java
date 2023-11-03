package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static BufferedReader inFromClient;
    static DataOutputStream outToClient;
    static double sum = 0;
    static double average = 0;
    static  double max = 0;
    static double min = 0;
    static String sendingSentence;

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        //read data received
        String receivedSentence = inFromClient.readLine();
        System.out.println(receivedSentence);

        String[] values = receivedSentence.split(","); //Split received String into multiple strings and putting those strings into an array to pass data as variables for calculate function
        for(int i = 0; i < values.length; i++){
            sum += Double.parseDouble(values[i]);
        }

        average = sum/values.length;

        max = Double.parseDouble(values[0]);
        for(int i = 0; i < values.length; i++){
            if (max < Double.parseDouble(values [i])){
                max = Double.parseDouble(values[i]);
            }
        }
        min = Double.parseDouble(values[0]);
        for(int i = 0; i < values.length; i++){
            if (min > Double.parseDouble(values [i])){
                min = Double.parseDouble(values[i]);
            }
        }
        sendingSentence = sum + "," + average + "," + max + "," + min;

        //send data to client
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        outToClient.writeBytes(sendingSentence);

        //welcomeSocket.close();
    }
}
