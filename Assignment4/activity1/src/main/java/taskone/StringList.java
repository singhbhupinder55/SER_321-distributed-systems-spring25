package taskone;

import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public synchronized int size() {
        return strings.size();
    }

    public synchronized String toString() {
        return strings.toString();
    }

    public String get(int index) {
        if (index >= 0 && index < strings.size()) {
            return strings.get(index);
        } else {
            return "Invalid index"; // Error message if index is out of bounds
        }
    }

}