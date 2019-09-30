package dbms;

import java.util.*;

import java.util.ArrayList;

public class Table {
    private String tableName;
    private final String[] entityAttributeString;
    private final Class[] entityDomainClass;
    private final String[] entityDomainString;
    private Integer[] entityDomainCharCount;
    private ArrayList<ArrayList<Object>> tableArrayList;
    private String[] primaryKeyString;
    private Integer[] primaryKeyInteger;
    private final int entityAttributeCount;
    private int tableRowCount;

    /*Create a empty table*/
    public Table(String tableName, String[] entityAttributeString, String[] entityDomainString,String[] primaryKeyString){
        Class[] domainClass = toEntityDomainClass(entityDomainString);
        this.entityDomainCharCount = countEntityDomainChar(entityDomainString);
        this.primaryKeyInteger = findPrimaryKey(entityAttributeString, primaryKeyString);
        this.tableName = tableName;
        this.entityAttributeString = entityAttributeString;
        this.entityDomainString = entityDomainString;
        this.entityDomainClass = domainClass;
        this.primaryKeyString = primaryKeyString;
        tableArrayList = new ArrayList<ArrayList<Object>>();
        this.entityAttributeCount = entityAttributeString.length;
        this.tableRowCount = 0;
        if(this.entityAttributeString.length != this.entityDomainClass.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /*set table*/
    public void setTableArrayList(ArrayList<ArrayList<Object>> tableArrayList) {
        this.tableArrayList = tableArrayList;
    }
    /*get attributes of a table*/
    public String[] getEntityAttributeString(Table tableObject) {
        return tableObject.entityAttributeString;
    }
    /*get an array string of domain of a table*/
    public String[] getEntityDomainString(Table tableObject) {
        return tableObject.entityDomainString;
    }
    /*get a table*/
    public Table getTable(Table tableObject) {
        return tableObject;
    }
    /*get table name*/
    public String getTableName(Table tableObject) {
        return tableObject.tableName;
    }

    /*Given an array of entity domain string (String), convert to array of entity domain class(Integer, String)*/
    public Class[] toEntityDomainClass(String[] entityDomainString) {
        Class[] currentDomainClass = new Class[entityDomainString.length];
        this.entityDomainCharCount = new Integer[entityDomainString.length];
        for(int i = 0; i < entityDomainString.length; i++) {
            String currentDomainString = entityDomainString[i];
            if(currentDomainString.equals("INTEGER")) {
                currentDomainClass[i] = Integer.class;
                this.entityDomainCharCount[i] = 2^63 - 2;
            }
            else if(currentDomainString.substring(0,7).equals("VARCHAR")) {
                currentDomainClass[i] = String.class;
                int endIndex = currentDomainString.indexOf(')');
                int varcharLimit = Integer.parseInt(currentDomainString.substring(8,endIndex));//get the number between ()
                this.entityDomainCharCount[i] = varcharLimit;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        return currentDomainClass;
    }


    /*store the maximum number of characters allowed in each column into Integer[] domainCharCount*/
    public Integer[] countEntityDomainChar(String[] entityDomainString) {
        this.entityDomainCharCount = new Integer[entityDomainString.length];
        for(int i = 0; i < entityDomainString.length; i++) {
            String currDomain = entityDomainString[i];
            if(currDomain.equals("INTEGER")) {
                this.entityDomainCharCount[i] = 2147483647 ;
            }
            else if(currDomain.substring(0,7).equals("VARCHAR")) {
                int endIndex = currDomain.indexOf(')');
                int varcharLimit = Integer.parseInt(currDomain.substring(8,endIndex));//get the number between ()
                this.entityDomainCharCount[i] = varcharLimit;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        return this.entityDomainCharCount;
    }

    /*Store the index of attributes with primary key*/
    public Integer[] findPrimaryKey(String[] _domainString, String[] _primaryKey) {
        this.primaryKeyInteger = new Integer[_primaryKey.length];
        for(int i = 0; i < _primaryKey.length; i++) {
            for(int j = 0; j < _domainString.length; j++) {
                if( _primaryKey[i].equals(_domainString[j])) {
                    this.primaryKeyInteger[i] = j;
                }
            }
        }
        for(int i = 0; i < _primaryKey.length; i++) {
            if(this.primaryKeyInteger[i] == null)
                this.primaryKeyInteger[i] = -1;
        }
        //if all elements in primaryKeyIndex is Null, then set length to 0
        Boolean isNull = true;
        for(int index: this.primaryKeyInteger) {
            if(index != -1) {
                isNull = false;
            }
        }
        if(isNull) {
            this.primaryKeyInteger = new Integer[0];
        }
        return this.primaryKeyInteger;
    }

    /*Returns an empty table with the same schema, new name
    Used in union, difference, and product*/
    public Table getEmptyTable(String newTableName) {
        Table newTable = new Table(newTableName, entityAttributeString, entityDomainString, primaryKeyString);
        return newTable;
    }

    /*Returns our tables tuples
    Used in union, difference, and product*/
    public ArrayList<ArrayList<Object>> getTuples() {
        return tableArrayList;
    }

    public String[] getEntityAttributeString() {
        return entityAttributeString;
    }

    public String[] getDomainStr() {
        return entityDomainString;
    }

    public String[] getPrimaryKeyString() {
        return primaryKeyString;
    }

    /*Insert a tuple into a table*/
    public void insert(ArrayList<Object> insertArrayList ) throws DuplicateKeyException{
        if(this.entityAttributeCount != insertArrayList.size()) {
            throw new IndexOutOfBoundsException();
        }
        for(int i = 0; i < this.entityAttributeCount; i++ ) {

            if(!this.entityDomainClass[i].equals(insertArrayList.get(i).getClass())) {//check if all tuple elem matches domain type
                throw new IllegalArgumentException();
            }

            if(insertArrayList.get(i) instanceof String ) {//check char input char count
                String currElement = (String) insertArrayList.get(i);
                if( currElement.length() > this.entityDomainCharCount[i]) {
                    currElement = currElement.substring(0,this.entityDomainCharCount[i] - 1);
                    insertArrayList.set(i, currElement);
                }

            }
        }

        if(this.primaryKeyInteger.length != 0) {
            for(int j = 0; j < tableArrayList.size(); j++) {//check if primary key has duplicate entries
                Boolean isDuplicate = true;
                for(int k = 0; k < this.entityAttributeString.length; k++) {
                    Object currElement = tableArrayList.get(j).get(k);
                    for(int m = 0; m < this.primaryKeyInteger.length; m++) {//check if k is key index
                        if(k == this.primaryKeyInteger[m] && !insertArrayList.get(k).equals(currElement)) {
                            isDuplicate = false;
                        }
                    }
                }
                if(isDuplicate) {
                    throw new DuplicateKeyException();
                }

            }
        }
        tableArrayList.add(insertArrayList);
        this.tableRowCount += 1;
    }

    /*insert a relation into another relation*/
    public void insertRelation(Table _relationName) throws DuplicateKeyException {
        for(ArrayList<Object> row : _relationName.tableArrayList) {
            this.insert(row);
        }
    }

    /*rename a table*/
    public Table rename(String _newTableName, String[] _newAttributes) throws DuplicateKeyException {
        Table newTable = new Table(_newTableName, _newAttributes, entityDomainString, primaryKeyString);
        for(ArrayList<Object> row: tableArrayList) {
            newTable.insert(row);
        }
        return newTable;
    }

    /*check if a string array contains another string array*/
    public static Boolean find(String[] str1, String[] str2) {
        Boolean found = false;
        for(String subStr1:str1) {
            found = false;
            for(String subStr2:str2) {
                if(subStr1.equals(subStr2)) {
                    found = true;
                }
            }
        }
        return found;
    }
    /*store the index of column to be modified into an integer array */
    public int[] findCol(String[] _newAttributes) {
        int[] updateColNum = new int[_newAttributes.length];
        for(int i = 0; i < _newAttributes.length; i++) {
            for(int j = 0; j < this.entityAttributeCount; j++) {//record the index of changed column
                if(_newAttributes[i] == this.entityAttributeString[j]) {
                    updateColNum[i] = j;
                }
            }
        }
        return updateColNum;
    }

    /*for each condition, find the index of qualified rows, store them in a list, then store all the lists into a listList*/
    public ArrayList<ArrayList<Integer>>  findUpdateRow(int[] _conditionColNum, String[] conditionAttribute, String[] conditionMark, Object[] conditionValue, String[] _andOr) {
        ArrayList<ArrayList<Integer>> listList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> updateRowNum = new ArrayList<Integer>();
        for(int i = 0; i < _conditionColNum.length; i++) {
            updateRowNum = new ArrayList<Integer>();
            for(int k = 0; k < this.tableArrayList.size(); k++) {			//for each row
                ArrayList<Object> currRow = this.tableArrayList.get(k);
                int colNum = _conditionColNum[i];
                String op = conditionMark[i];
                if(op.equals("==")) {
                    if(currRow.get(colNum).equals(conditionValue[i])) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
                else if(op.equals("!=")) {
                    if(!currRow.get(colNum).equals(conditionValue[i])) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
                else if(op.equals("<")){
                    int currInt = (int) currRow.get(colNum);
                    int condInt = (int) conditionValue[i];
                    if(currInt < condInt) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
                else if(op.equals(">")){
                    int currInt = (int) currRow.get(colNum);
                    int condInt = (int) conditionValue[i];
                    if(currInt > condInt) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
                else if(op.equals("<=")){
                    int currInt = (int) currRow.get(colNum);
                    int condInt = (int) conditionValue[i];
                    if(currInt <= condInt) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
                else if(op.equals(">=")){
                    int currInt = (int) currRow.get(colNum);
                    int condInt = (int) conditionValue[i];
                    if(currInt >= condInt) {
                        updateRowNum.add(this.tableArrayList.indexOf(currRow));
                    }
                }
            }
            listList.add(updateRowNum);
        }
        return listList;
    }
    /*|| operation in two lists*/
    public static ArrayList<Integer> orOperation(ArrayList<Integer> orOperationArrayList1, ArrayList<Integer> orOperationArrayList2) {
        ArrayList<Integer> rowNumberUpdate = new ArrayList<Integer>();
        HashSet<Integer> orSet = new HashSet<Integer>();
        for(int list1Num:orOperationArrayList1) {
            orSet.add(list1Num);
        }
        for(int list2Num:orOperationArrayList2) {
            orSet.add(list2Num);
        }
        for(int hashNum:orSet) {
            rowNumberUpdate.add(hashNum);
        }
        return rowNumberUpdate;

    }
    /*&& operation in two lists*/
    public static ArrayList<Integer> andOperation(ArrayList<Integer> andOperationArrayList1, ArrayList<Integer> andOperationArrayList2) {
        ArrayList<Integer> updateRowNumArray = new ArrayList<Integer>();
        HashSet<Integer> s1 = new HashSet<Integer>(andOperationArrayList1);
        HashSet<Integer> s2 = new HashSet<Integer>(andOperationArrayList2);
        s1.retainAll(s2);

        Integer[] updateRowNum = s1.toArray(new Integer[s1.size()]);
        for(int num:updateRowNum) {
            updateRowNumArray.add(num);
        }
        return updateRowNumArray;
    }
    /*find the index of row to be updated*/
    public static ArrayList<Integer> updateRowIndex(ArrayList<ArrayList<Integer>> _listList, String[] _andOr){
        ArrayList<Integer> updateRowNum = new ArrayList<>(_listList.get(0));
        if(_andOr.length != 0) {
            for(int k = 0; k < _andOr.length; k++) {
                if(_andOr[k] == "&&") {
                    updateRowNum = andOperation(updateRowNum, _listList.get(k+1));
                }
                else if(_andOr[k] == "||") {
                    updateRowNum = orOperation(updateRowNum, _listList.get(k+1));
                }
            }
        }
        return updateRowNum;
    }
    /*update the row that qualifies condition*/
    public void update(String[] _newAttributes,Object[] _newValues,String[] _conditionAttribute, String[] _conditionMark, Object[] _conditionValue, String[] _andOr) {
        if(!(find(_newAttributes, this.entityAttributeString)&&find(_conditionAttribute, this.entityAttributeString))){//check if SQL input field valid
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < _newValues.length; i++) {
            Object o = _newValues[i];
            if(!o.getClass().equals(this.entityDomainClass[i])){
                throw new IllegalArgumentException();
            }
        }
        int[] updateCol = findCol(_newAttributes);
        int[] conditionCol = findCol(_conditionAttribute);
        ArrayList<ArrayList<Integer>> listList  = findUpdateRow(conditionCol, _conditionAttribute, _conditionMark, _conditionValue, _andOr);
        ArrayList<Integer> updateRowIndex = updateRowIndex(listList, _andOr);
        for(int updateRowNum:updateRowIndex) {
            ArrayList<Object> updateRow = tableArrayList.get(updateRowNum);
            for(int i = 0; i < updateCol.length; i++) {
                int updateColNum = updateCol[i];
                updateRow.set(updateColNum, _newValues[i]);
            }
        }

    }
    /*delete the row that qualifies condition*/
    public void delete(String[] _conditionAttribute, String[] _conditionMark, Object[] _conditionValue, String[] _andOr) {
        int[] conditionCol = findCol(_conditionAttribute);
        ArrayList<ArrayList<Integer>> listList  = findUpdateRow(conditionCol, _conditionAttribute, _conditionMark, _conditionValue, _andOr);
        ArrayList<Integer> updateRowIndex = updateRowIndex(listList, _andOr);
        for(int rowNum:updateRowIndex) {
            this.tableArrayList.remove(rowNum);
            for(int i = 0; i < updateRowIndex.size(); i++) {
                updateRowIndex.set(i, updateRowIndex.get(i) - 1);//when a row is removed, the table size change, and the marked row index should change too
            }
        }
    }

    /*select the row that meets condition*/
    public Table select(String _newRelationName, String[] _conditionAttribute, String[] _conditionMark, Object[] _conditionValue, String[] _andOr) throws DuplicateKeyException {
        Table newTable = new Table(_newRelationName, this.entityAttributeString, entityDomainString, primaryKeyString);
        int[] conditionCol = findCol(_conditionAttribute);
        ArrayList<ArrayList<Integer>> listList  = findUpdateRow(conditionCol, _conditionAttribute, _conditionMark, _conditionValue, _andOr);
        ArrayList<Integer> updateRowIndex = updateRowIndex(listList, _andOr);
        for(int rowNum:updateRowIndex) {
            ArrayList<Object> selectedRow = tableArrayList.get(rowNum);
            newTable.insert(selectedRow);
        }
        return newTable;
    }

    //given projected attributes, find the corresponding index of attribute
    public int[] findIndex(String[] _projectAttributes) {
        ArrayList<String> projectAttributes = new ArrayList<String>();
        ArrayList<String> origAttributes = new ArrayList<String>();
        int[] index = new int[_projectAttributes.length];
        for(int i = 0; i < _projectAttributes.length; i++) {
            projectAttributes.add(_projectAttributes[i]);
        }
        for(int i = 0; i < this.entityAttributeString.length; i++) {
            origAttributes.add(this.entityAttributeString[i]);
        }
        for(int i = 0; i < _projectAttributes.length; i++) {
            index[i] = origAttributes.indexOf(projectAttributes.get(i));
        }
        return index;
    }
    /*project operation*/
    public Table project(String _newRelationName, String[] _projectAttributes) throws DuplicateKeyException {
        int[] projectAttributeIndex = findIndex(_projectAttributes);
        String[] projectDomain = new String[_projectAttributes.length];
        ArrayList<String> origDomain = new ArrayList<String>();
        for(int i = 0; i < entityDomainString.length; i++) {
            origDomain.add(entityDomainString[i]);
        }

        for(int i = 0; i < _projectAttributes.length; i++) {
            projectDomain[i] = origDomain.get(projectAttributeIndex[i]);
        }

        String[] projectPrimaryKey = new String[0];
        Table newTable = new Table(_newRelationName, _projectAttributes, projectDomain, projectPrimaryKey );
        ArrayList<ArrayList<Object>> projectTable = new ArrayList<ArrayList<Object>>(tableArrayList.size());

        for(int i = 0; i < tableArrayList.size(); i++) {//populate projectTable
            ArrayList<Object> origRow = tableArrayList.get(i);
            ArrayList<Object> newRow = new ArrayList<Object>();
            for(int attributeIndex:projectAttributeIndex) {
                newRow.add(origRow.get(attributeIndex));
            }
            projectTable.add(newRow);
        }
        Set<ArrayList<Object>> hs = new HashSet<>();//remove duplicate
        hs.addAll(projectTable);
        projectTable.clear();
        projectTable.addAll(hs);
        for(ArrayList<Object> projectRow:projectTable) {
            newTable.insert(projectRow);
        }
        return newTable;

    }




    /*print table*/
    @Override
    public String toString() {
        String s;
        String line = "";
        s = " " + tableName + "\n" + "------------------------------------------------------------------" + "\n";
        for(int i = 0; i< entityAttributeString.length; i++) {

            line += " " + entityAttributeString[i] ;

            while(line.length()<=20) {
                line += " ";
            }

            if(i != entityAttributeString.length-1) {
                line += "|";
            }

            s += line;
            line = "";
        }

        s += "|\n";

        for(int i = 0; i< tableArrayList.size(); i++) {
            ArrayList<Object> currRow = tableArrayList.get(i);
            for(int j = 0; j<this.entityAttributeString.length; j++) {
                line += " " + currRow.get(j) ;
                while(line.length()<=20) {
                    line += " ";
                }

                line += "|";
                s += line;
                line = "";
            }

            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        try {
            String tableName = "animals";
            String[] attributes = {"Name","Kind","Years"};
            String[] domainString = {"VARCHAR(20)","VARCHAR(8)","INTEGER"};
            String[] primaryKey = {"Name","Kind"};
            Table animals = new Table(tableName,attributes,domainString,primaryKey);
            ArrayList<Object> newTuple = new ArrayList<Object>(Arrays.asList("Joe","cat",4));
            ArrayList<Object> newTuple1 = new ArrayList<Object>(Arrays.asList("Spot","dog",10));
            ArrayList<Object> newTuple2 = new ArrayList<Object>(Arrays.asList("Snoopy","dog",10));
            ArrayList<Object> newTuple3 = new ArrayList<Object>(Arrays.asList("Tweety","bird",1));
            ArrayList<Object> newTuple4 = new ArrayList<Object>(Arrays.asList("Joe","bird",2));
            animals.insert(newTuple);
            animals.insert(newTuple1);
            animals.insert(newTuple2);
            animals.insert(newTuple3);
            animals.insert(newTuple4);
            animals.toString();
            String[] conditionAttribute = {"Years"};
            String[] conditionMark = {"=="};
            Object[] conditionValue = {10};
            String[] andOr = {};
            animals.delete(conditionAttribute, conditionMark, conditionValue, andOr);
            animals.toString();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Please make sure number of attributes equals to number of domain(constraints)");
            e.printStackTrace();
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("Please make sure number of elements in each row is equal to number of attributes");
            e.printStackTrace();
        }
        catch(IllegalArgumentException e) {
            System.out.println("Please make sure the type of each element matches the type/character count required by the attribute");
            System.out.println("If you are updating, please make sure your field name/entry value is valid");
            e.printStackTrace();

        }
        catch(DuplicateKeyException e) {
            System.out.println("Please do not insert tuple with duplicate primary key value");
            e.printStackTrace();
        }
    }
}