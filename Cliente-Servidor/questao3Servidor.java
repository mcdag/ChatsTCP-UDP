import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String [] args) throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(8888);
        String msgConvertida = "";
        InetAddress ipCliente;
        int porta;
        
        while (true) {
            byte[] receberDados = new byte[1024];
            byte[] enviarDados = new byte[1024];
        	DatagramPacket receberPacote = new DatagramPacket(receberDados, receberDados.length);
            serverSocket.receive(receberPacote);
            ipCliente = receberPacote.getAddress();
            porta = receberPacote.getPort();
            msgConvertida = new String(receberDados, "UTF-8");
            System.out.println(msgConvertida);
            
            if (msgConvertida.length() > 5 && msgConvertida.substring(0,5).equals("Tchau")) {
                String sentenca = "Adeus";
                enviarDados = sentenca.getBytes();
                DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipCliente, porta);
                serverSocket.send(enviarPacote);
                break;
            }
        }
    }
}
