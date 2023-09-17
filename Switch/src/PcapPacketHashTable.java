import java.util.*;

import org.jnetpcap.packet.PcapPacket;

public class PcapPacketHashTable {

    private static PcapPacketHashTable instance = null;
    private final LinkedHashMap<Integer, PcapPacket> map;


    private PcapPacketHashTable() {
        this.map = new LinkedHashMap<>();;
    }

    public static synchronized PcapPacketHashTable getInstance() {
        if (instance == null) {
            instance = new PcapPacketHashTable();
        }
        return instance;
    }

    public synchronized void put(PcapPacket packet) {

        int key = calculateHashCode(packet);
        System.out.println("NEW PACKET hash : "+key);
        this.map.put(key, packet);
    }

    public synchronized PcapPacket get(int key) {
        return this.map.get(key);
    }
    public synchronized void remove(PcapPacket packet) {
        int key = calculateHashCode(packet);
        this.map.remove(key);
    }


    public synchronized boolean contains(PcapPacket packet) {
        int key = calculateHashCode(packet);


        return this.map.containsKey(key);
    }

    private int calculateHashCode(PcapPacket packet) {
        byte[] byteArray = packet.getByteArray(0, packet.size());
        return Arrays.hashCode(byteArray);
    }

    public synchronized int getSize() {
        return this.map.size();
    }
    public synchronized void removeOldestEntries() {
        int currentSize = this.map.size();
        int removalSize = (int) (currentSize * 0.8); // calculate the number of entries to remove

        if (removalSize > 0) {
            List<Integer> keysToRemove = new ArrayList<>();

            // find the oldest entries by iterating over the entry set of the linked hash map
            Iterator<Map.Entry<Integer, PcapPacket>> iterator = this.map.entrySet().iterator();
            for (int i = 0; i < removalSize; i++) {
                Map.Entry<Integer, PcapPacket> entry = iterator.next();
                keysToRemove.add(entry.getKey());
            }

            // remove the oldest entries from the linked hash map
            for (Integer key : keysToRemove) {
                this.map.remove(key);
            }
        }
    }


}
