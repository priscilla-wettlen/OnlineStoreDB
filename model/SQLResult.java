package model;

public class SQLResult {
    String[][] tableData;

    public SQLResult(String[][] tableData) {
        this.tableData = tableData;
    }

    public void printTableData(){
        for (int i = 0; i < tableData.length; i++) {
            for (int j = 0; j < tableData[i].length; j++) {
                System.out.println(tableData[i][j] + "hej" + "\t");
            }
        }
    }

    public void printData(){
        printTableData();
    }
}
