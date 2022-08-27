package jrails;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    This class is used to write and read data to/from db file
 */
public class Dao {
    private final String formats = ".txt";

    public boolean fileExist_create(String name){
        try {
            File file = new File(name+formats);
            if(!file.exists()){
                file.createNewFile();
                String data = "id=0";
                return this.datawrite(name,data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /*
    save a new row in the target name file
     */
    public boolean saveModel(String name,HashMap<String,String> data){
        //first save id number(it should plus 1)
        int id = this.readID(name);
        String lines = "id="+String.valueOf(id+1)+"\n"+this.readALLData(name);
        for(Map.Entry<String,String> entry:data.entrySet()){
            lines+=entry.getKey()+"&";
            if(entry.getValue() == null){//value is null
                lines+="^_^,";
            }else{
                lines+=entry.getValue()+",";
            }
        }
        String datas = lines.substring(0,lines.length()-1);
        boolean save_res = this.datawrite(name,datas);
        return save_res;
    }

    /*
    modify a row in the target name file(id must smaller than row size)
     */
    public boolean replaceModel(String name,HashMap<String,String> data,int id){
        List<String> list = new ArrayList<String>();
        String modified_datas = "";
        try {
            File file = new File(name+formats);
            if(!file.exists()){
                throw new RuntimeException();
            }
            FileReader reader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                list.add(lineTxt);
            }
            bufferedReader.close();
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        //replace id model data,before and after id num will be unmodified
        for(int i=0;i<id;i++){//data before id
            modified_datas +=list.get(i)+"\n";
        }

        //data of id
        for(Map.Entry<String,String> entry:data.entrySet()){
            modified_datas+=entry.getKey()+"&";
            if(entry.getValue() == null){//value is null
                modified_datas+="^_^,";
            }else{
                modified_datas+=entry.getValue()+",";
            }
        }
        modified_datas = modified_datas.substring(0,modified_datas.length()-1);
        if(id!= (list.size()-1)){ //doesn't replace last row
            modified_datas += "\n";
        }

        for(int i=id+1;i<list.size();i++){//data after id
            if(i!=list.size()-1){
                modified_datas += list.get(i)+"\n";
            }else{
                modified_datas += list.get(i);
            }
        }

        return this.datawrite(name,modified_datas);
    }

    /*
    write all the data to target file,(not append)
    return:false -- write failed
            true -- write success
     */
    private boolean datawrite(String name,String data){
        try {
            File file = new File(name+formats);

            //create new file if not exists
            if(!file.exists()){
//                file.createNewFile();
                return false;
            }

            FileWriter writer = new FileWriter(file.getName());
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(data);
            bufferedWriter.close();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /*
    read one data according to id
    return:HashMap -- key:col name val:value
    */
    public HashMap<String,String> readOneData(String name, int id){
        HashMap<String,String> result = new HashMap<>();
        List<String> list = new ArrayList<String>();

        try {
            File file = new File(name+formats);
            if(!file.exists()){
                throw new RuntimeException();
            }
            FileReader reader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineTxt = null;
            //read id;which is the first line
            //bufferedReader.readLine();

            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                list.add(lineTxt);
            }
            bufferedReader.close();
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        //the format of each row is colum1&value,colum2&value,....
        String data = list.get(id);
        String[] colums = data.split(",",-1);
        for(int i=0;i<colums.length;i++){
            String[] col = colums[i].split("&",2);
            if(col[1].equals("^_^")){
                //string is null;(we define ^_^ represent null)
                result.put(col[0],null);
            }else{
                result.put(col[0],col[1]);
            }
        }

        return result;
    }

    /*
    read id from table(id should be in first line)
    */
    public int readID(String name){
        String firstLine=null;
        try {
            File file = new File(name+formats);
            if(!file.exists()){
                return 0;
            }
            FileReader reader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(reader);

            //read id;which is the first line
            firstLine = bufferedReader.readLine();
            bufferedReader.close();
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine==null) return 0;
        String[] ids = firstLine.split("=");
        return Integer.parseInt(ids[1]);
    }
    /*
    read all the row data(Model data) in target file
     */
    public String readALLData(String name){
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("The current working directory is " + currentDirectory);
        String lines= "";
        try {
            File file = new File(name+formats);
            if(!file.exists()){
                throw new RuntimeException();
            }
            FileReader reader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineTxt = null;
            String id = bufferedReader.readLine();//the first line is id value
            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                lines += lineTxt+"\n";

            }
            bufferedReader.close();
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return lines;
    }

    /*
    delete all rows in the target name file
     */
    public void deleteModel(String name){
        String data = "id=0";
        this.datawrite(name,data);
    }

}
