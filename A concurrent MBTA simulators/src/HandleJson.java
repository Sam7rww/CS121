import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleJson {

    public JsonInfo readJsonStream(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        JsonReader reader = new JsonReader(bufferedReader);
        try {
            return readMessage(reader);
        } finally {
            reader.close();
        }
    }

    public JsonInfo readMessage(JsonReader reader) throws IOException {
        Map<String, List<String>> lines = null;
        Map<String, List<String>> trips = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("lines")) {
                lines = readLinesAndTrips(reader);
            } else if (name.equals("trips")) {
                trips = readLinesAndTrips(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new JsonInfo(lines,trips);
    }

    public Map<String, List<String>> readLinesAndTrips(JsonReader reader)throws IOException{
        Map<String, List<String>> res = new HashMap<>();

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            List<String> strings = readStringArray(reader);
            res.put(name,strings);
        }
        reader.endObject();
        return res;
    }

//
//    public Map<String, List<String>> readtrips(JsonReader reader)throws IOException{
//        Map<String, List<String>> trips = new HashMap<>();
//
//        reader.beginObject();
//        while (reader.hasNext()){
//            String name = reader.nextName();
//            List<String> strings = readStringArray(reader);
//            trips.put(name,strings);
//        }
//        reader.endObject();
//        return trips;
//    }


    public List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

}
