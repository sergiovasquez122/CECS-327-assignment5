import java.net.SocketException;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EchoClient {
    public static void main(String args[]) {
        Scanner keyboard = new Scanner(System.in);
        DatagramSocket aSocket = null;
        try {
            // Instantiates a socket
            aSocket = new DatagramSocket();

            // "192.168.100.104"
            //Debug: InetAddress LHINet = InetAddress.getByName("192.168.100.104");
            System.out.println("\nPlease enter a Local Host Internet Address");
            String LHIinput = keyboard.nextLine();

            //Checks to see if only numbers and periods exist in the LHIinput
            while(Pattern.matches("[a-zA-Z]+", LHIinput) == true)
            {
                System.out.println("\nEnter a valid Local Host Internet Address");
                LHIinput= keyboard.nextLine();
            }
            // Gets the Internet Address
            InetAddress LHINet = InetAddress.getByName(LHIinput);

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

            int serverPort = Integer.parseInt(serverInput);


            // Gets user input and translates to a byte array for the Datagram
            // Packet
            while(true) {
                System.out.println("\nPlease enter some text");
                String in = keyboard.nextLine();
                byte[] userInput = in.getBytes();


                // Creates DatagramPacket containg key information to send a request
                // to server.
                DatagramPacket request = new DatagramPacket(userInput, userInput.length, LHINet, serverPort);
                aSocket.send(request);

                byte[] buffer = new byte[1000];

                // Gets the reply from the server
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("\nReply from Server: " + new String(reply.getData()));
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }

        finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}