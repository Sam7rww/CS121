package jrails;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    //id number
    private int id;

    private static Dao dao = new Dao();

    private static List<String> DBmodels = new ArrayList<>();

    public Model(){
        id = 0;
    }

    public void save() {
        /* this is an instance of the current model */
        Class current = this.getClass();
        String name = current.getName();
        //model data hashMap
        HashMap<String,String> data = new HashMap<>();

        for(Field f:current.getFields()){
            Annotation a = f.getAnnotation(Column.class);
            if(a!=null){
                try {
                    String key = f.getName();
                    String val = String.valueOf(f.get(this));
                    data.put(key,val);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //add to DBmodels
        if(!DBmodels.contains(name)){
            DBmodels.add(name);
        }

        //initial table or judge whether the table is exist
        boolean table_exist = dao.fileExist_create(name);
        if(!table_exist) throw new RuntimeException();

        if(id == 0){
            id = dao.readID(name)+1;
            dao.saveModel(name,data);
        }else{
            if(id>(dao.readID(name)))  throw new RuntimeException();
            dao.replaceModel(name,data,id);
        }

    }

    public int id() {
//        dao.readALLData("test1");
        return id;
    }

    public static <T> T find(Class<T> c, int id) {
        String name = c.getName();
        int max_id = dao.readID(name);
        if(max_id == 0) return null;//there is no such db
        if(id>max_id){
            //designated id isn't exist
            return null;
        }
        HashMap<String,String> data = dao.readOneData(name,id);
        T o = null;
        try {
            o = c.getConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        //initial fields value
        try {
            for(Map.Entry<String,String> en:data.entrySet()){
                if(en.getValue().equals("@_@")) return null;   //this row data has been destroyed
                Field field = c.getField(en.getKey());
                //certify the type of field
                Type type = field.getGenericType();
                if(type == String.class){
                    field.set(o,en.getValue());
                }else if(type == int.class){
                    field.setInt(o,Integer.parseInt(en.getValue()));
                }else if(type == boolean.class){
                    field.setBoolean(o,Boolean.parseBoolean(en.getValue()));
                }else {
                    throw new RuntimeException();
                }
//                else if(type == float.class){
//                    field.setFloat(o,Float.parseFloat(en.getValue()));
//                }else if(type == char.class){
//                    field.setChar(o,en.getValue().charAt(0));
//                }else if(type == double.class){
//                    field.setDouble(o,Double.parseDouble(en.getValue()));
//                }
            }
            Method setid = c.getMethod("setId",int.class);
            setid.invoke(o,id);
        }catch (Exception e){
            System.out.println("the process of field value in Model find is error");
            e.printStackTrace();
        }

        return o;
    }

    public static <T> List<T> all(Class<T> c) {
        // Returns a List<element type>
        String name = c.getName();
        List<T> lists = new ArrayList<>();
        int max_id = dao.readID(name);
        if(max_id == 0) return lists;//there is no such db
        for(int i=1;i<=max_id;i++){
            T temp = Model.find(c,i);
            if(temp != null){
                lists.add(temp);
            }
        }
        return lists;
    }

    /*
    the row is destroyed, his value will be reset as "@_@"
     */
    public void destroy() {
        Class current = this.getClass();
        String name = current.getName();
        if(id == 0) throw  new RuntimeException();
        if(id > dao.readID(name)) throw new RuntimeException();

        //model data hashMap
        HashMap<String,String> data = new HashMap<>();

        for(Field f:current.getFields()){
            Annotation a = f.getAnnotation(Column.class);
            if(a!=null){
                try {
                    String key = f.getName();
                    String val = "@_@";
                    data.put(key,val);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        dao.replaceModel(name,data,id);
        id = 0;
    }

    public static void reset() {
        for(String name:DBmodels){
            dao.deleteModel(name);
        }
    }

    public void setId(int i){
        id = i;
    }

}
