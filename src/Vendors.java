import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Vendors{
    private HashMap<String, Vendor> allVendors = new HashMap<>();

    public void addVendors(String vendorName, Vendor v){
        allVendors.put(vendorName, v);
    }

    @Override
    public String toString(){
        String s = "";
        Vendor temp;
        List<String> list = new ArrayList<>(allVendors.keySet());
        Collections.sort(list);
        for(String i : list){
            temp = allVendors.get(i);
            s += "Vendor Name: " + i + "\n";
            s += temp.toString();
        }
        return s;
    }

    /**
     * Prints the 5 vendors passed as parameters using their to string method and name.
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     * @return
     */
    public String print5Vendors(String v1, String v2, String v3, String v4, String v5){
        if( !(allVendors.containsKey(v1) && allVendors.containsKey(v2) && allVendors.containsKey(v3) && allVendors.containsKey(v4) && allVendors.containsKey(v5)))
            return "Invalid vendor list";
        String s = "Vendor Name: " + v1 + "\n" + allVendors.get(v1).toString()
                + "Vendor Name: " + v2 + "\n" + allVendors.get(v2).toString()
                + "Vendor Name: " + v3 + "\n" + allVendors.get(v3).toString()
                + "Vendor Name: " + v4 + "\n" + allVendors.get(v4).toString()
                + "Vendor Name: " + v5 + "\n" + allVendors.get(v5).toString();
        System.out.println(s);
        return s;
    }
}
