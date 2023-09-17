import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class test {
    public static void main(String[] args) {
        try {



            InetAddress localAddress = InetAddress.getByName("147.175.180.234");
            System.out.println("HMMM: "+ localAddress);
            InetAddress address = InetAddress.getByName("87.249.132.165");
            System.out.println("HMMM: "+ address);
            InetAddress source = InetAddress.getByAddress(localAddress.getAddress());
            boolean isReachable = address.isReachable(NetworkInterface.getByInetAddress(source),0, 5000);
            if (isReachable) {
                System.out.println("Pinging "+ address);
            } else {

                System.out.println("No reply from "+ address);

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}