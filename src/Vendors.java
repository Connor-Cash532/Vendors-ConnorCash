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
}
