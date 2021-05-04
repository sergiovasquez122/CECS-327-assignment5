import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EchoServer {
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        Scanner keyboard = new Scanner(System.in);
        try {
        
            // Gets serverPort from cmdline
            //Debug: String serverInput = "6789";
            System.out.println("\nPlease enter a Server Port");
            String serverInput = keyboard.nextLine();

            //Checks to see if only numbers exist
            while(Pattern.matches("[a-zA-Z]+", serverInput) == true)
            {
                System.out.println("\nEnter a valid Server Port");
                serverInput = keyboard.nextLine();
            }

            aSocket = new DatagramSocket(Integer.parseInt(serverInput));
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                System.out.println("\nRequest received.");
                
            	String temp = new String(request.getData());
            System.out.println("\nOriginal Message:  " + temp);
            	temp = temp.toUpperCase();

            	byte[] capString = new byte[1024];
            	capString = temp.getBytes();
            
                DatagramPacket reply = new DatagramPacket(capString, request.getLength(), request.getAddress(),
                        request.getPort());
                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket:  " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:  " + e.getMessage());
        }

        finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}
