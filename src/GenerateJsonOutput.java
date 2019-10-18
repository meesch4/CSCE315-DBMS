import com.google.gson.*;
import dbms.*;
import types.IntType;
import types.Type;
import types.Varchar;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GenerateJsonOutput {
    public static void main(String[] args) throws IOException {
        IDbms iDbms = new Dbms();
        GenerateJsonOutput jsonOutput = new GenerateJsonOutput();
        String filePath = "Sample Data/movies_single.json";
        jsonOutput.processMoviesFile(filePath, iDbms, "movies");
         filePath = "Sample Data/credits_single.json";
        jsonOutput.processMoviesFile(filePath, iDbms, "credit");
    }

    public void processMoviesFile(String filePath, IDbms iDbms, String tableName) throws IOException {
        Gson gson = new Gson();
        JsonArray movies = gson.fromJson(new FileReader(filePath), JsonArray.class);
        JsonObject object = movies.get(0).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
        List<Type> mainTableColumnTypes = new ArrayList<>();
        List<String> mainTableColumns = new ArrayList<>();
        List<String> mainTablePrimaryKeys = new ArrayList<>();
        List<Object> mainData = new ArrayList<>();
        mainTablePrimaryKeys.add("id");
        int mainId= object.get("id").getAsInt();
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (entry.getValue().isJsonObject()) {
                mainTableColumns.add(entry.getKey());
                mainTableColumnTypes.add(new IntType());
                JsonObject obj = entry.getValue().getAsJsonObject();
                int idVal=obj.get("id").getAsInt();
                mainData.add(idVal);
                List<String> columns = new ArrayList<>(obj.keySet());
                List<Type> columnTypes = new ArrayList<>();
                List<Object> data = new ArrayList<>();
                for (String key : obj.keySet()) {
                    JsonElement jsonElement = obj.get(key);
                    try {
                        int val = jsonElement.getAsInt();
                        columnTypes.add(new IntType());
                        data.add(val);
                    } catch (NumberFormatException e) {
                        columnTypes.add(new Varchar(1000));
                        data.add(jsonElement.getAsJsonPrimitive().getAsString());
                    }
                }
                List<String> primaryKeys = new ArrayList<>();
                primaryKeys.add("id");
                iDbms.createTable(entry.getKey(), columns, columnTypes, primaryKeys);
                iDbms.insertFromValues(entry.getKey(), data);
                savetoJson(entry.getKey(), iDbms);
            } else if (entry.getValue().isJsonArray()) {
                JsonArray asJsonArray = entry.getValue().getAsJsonArray();
                JsonElement value = asJsonArray.get(0);
                if (value.isJsonObject()) {
                    JsonObject obj = value.getAsJsonObject();
                    List<String> columns = new ArrayList<>(obj.keySet());
                    List<Type> columnTypes = new ArrayList<>();
                    for (String key : obj.keySet()) {
                        JsonElement jsonElement = obj.get(key);
                        try {
                            int val = jsonElement.getAsInt();
                            columnTypes.add(new IntType());
                        } catch (NumberFormatException e) {
                            columnTypes.add(new Varchar(1000));
                        }
                    }
                    List<String> primaryKeys = new ArrayList<>();
                    String primarayKey="id";
                    Type type=new IntType();
                    if(!obj.keySet().contains("id")){
                        primarayKey="name";
                        type=new Varchar(100);
                    }
                    primaryKeys.add(primarayKey);
                    iDbms.createTable(entry.getKey(), columns, columnTypes, primaryKeys);

                    for(int i = 0; i< asJsonArray.size(); i++){
                        JsonElement jsonElement= asJsonArray.get(i);
                        List<Object> data = new ArrayList<>();
                        for (String key : jsonElement.getAsJsonObject().keySet()) {
                            try {
                                int val = jsonElement.getAsJsonObject().get(key).getAsInt();
                                data.add(val);
                            } catch (NumberFormatException e) {
                                data.add(jsonElement.getAsJsonObject().get(key).getAsJsonPrimitive().getAsString());
                            }
                        }
                        iDbms.insertFromValues(entry.getKey(), data);
                    }
                    savetoJson(entry.getKey(), iDbms);

                    createMapperTable(iDbms, entry.getKey(), tableName,type,primarayKey+"2");
                    String tableName1 = tableName + "_" +  entry.getKey() + "_" + "mapper";

                   for(int i = 0; i< asJsonArray.size(); i++){
                       JsonElement jsonElement= asJsonArray.get(i);
                       if(jsonElement.getAsJsonObject().keySet().contains("id")) {
                           int idValue = jsonElement.getAsJsonObject().get("id").getAsInt();
                           List<Object> data = new ArrayList<>();
                           data.add(mainId);
                           data.add(idValue);
                           iDbms.insertFromValues(tableName1, data);
                       }else{
                           String idValue = jsonElement.getAsJsonObject().get("name").getAsString();
                           List<Object> data = new ArrayList<>();
                           data.add(mainId);
                           data.add(idValue);
                           iDbms.insertFromValues(tableName1, data);
                       }
                   }
                    savetoJson(tableName1, iDbms);
                }
            } else {
                mainTableColumns.add(entry.getKey());

                try {
                    int val = entry.getValue().getAsInt();
                    mainTableColumnTypes.add(new IntType());
                    mainData.add(val);
                } catch (NumberFormatException e) {
                    mainTableColumnTypes.add(new Varchar(1000));
                    mainData.add(entry.getValue().getAsJsonPrimitive().getAsString());
                }
            }
        }
        iDbms.createTable(tableName, mainTableColumns, mainTableColumnTypes, mainTablePrimaryKeys);
        iDbms.insertFromValues(tableName, mainData);
        savetoJson(tableName, iDbms);

    }

    private void savetoJson(String tableName, IDbms iDbms) throws IOException {
        TableRootNode tableRootNode = iDbms.getTable(tableName);
        HashMap<String, RowNode> rowMap = tableRootNode.getRowNodes();
        JsonArray jsonArray = new JsonArray();


        for (RowNode currRow : rowMap.values()) {
            ArrayList<Attribute> attributes = tableRootNode.getAttributes();
            JsonObject object = new JsonObject();
            for (int j = 0; j < currRow.getDataFields().length; j++) {
                Object data = currRow.getDataField(j);
                object.addProperty(attributes.get(j).getName(),data.toString());
            }
            jsonArray.add(object);
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();;
        File jsonFile = new File("json_output",tableName+".json");
        jsonFile.createNewFile();
        FileWriter writer = new FileWriter(jsonFile);
        gson.toJson(jsonArray, writer);
        writer.flush();
        writer.close();

    }

    /**
     * Create mapper table for multiple mapping
     *
     * @param iDbms
     * @param key
     * @param tableName
     */
    private void createMapperTable(IDbms iDbms, String key, String tableName, Type type, String columnName) {
        List<Type> columnTypes = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        columns.add("id1");
        columns.add(columnName);
        List<String> primaryKeys = new ArrayList<>();
        columnTypes.add(new IntType());
        columnTypes.add(type);
        primaryKeys.add("id1");
        primaryKeys.add(columnName);
        String tableName1 = tableName + "_" + key + "_" + "mapper";
        iDbms.createTable(tableName1, columns, columnTypes, primaryKeys);
    }

}
