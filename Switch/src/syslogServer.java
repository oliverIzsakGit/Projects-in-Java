import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;
public class syslogServer {

    private static final Logger LOGGER = Logger.getLogger(syslogServer.class.getName());
    private static final int PORT = 514;

    public syslogServer(String ipaddress) {
    }

    public static void main(String[] args) throws Exception {
        // specify the IP address to listen to
        InetAddress ipAddress = InetAddress.getByName("192.168.1.100");
        DatagramSocket socket = new DatagramSocket(PORT, ipAddress);
        byte[] buffer = new byte[1452];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            // parse the packet
            byte[] data = packet.getData();
            int length = packet.getLength();

            // check if packet is valid
            if (length < 6) {
                LOGGER.log(Level.WARNING, "Invalid packet received");
                continue;
            }

            // extract severity, timestamp, and message from packet
            int severity = data[0] & 0xff;
            int timestamp = (data[1] & 0xff) << 24 |
                    (data[2] & 0xff) << 16 |
                    (data[3] & 0xff) << 8 |
                    (data[4] & 0xff);
            String message = new String(data, 6, length - 6);

            // log the message
            LOGGER.log(Level.parse("SYSLOG" + severity), new Date(timestamp) + " " + message);
        }
    }

}