package model;

public class SQLResult {
    String[] data;
    int nbrOfColumns;
    int rowCounter;
    int maxRow;

    public SQLResult(String[] data, int nbrOfColumns)
    {
        this.data = data;
        this.nbrOfColumns = nbrOfColumns;
        rowCounter = 0;
        maxRow = data.length / nbrOfColumns;
    }

    public void printAll()
    {
        rowCounter = 0;
        System.out.println("rows to be printed: " + maxRow + " data size: " + data.length + " colums: " + nbrOfColumns);
        for(int i = 0; i < maxRow; i++)
        {
            
            printRow();
        }
    }

    public void printRow()
    {
        String temp = "";
        for(int i = 0; i < nbrOfColumns; i++)
        {
            temp = temp + data[(rowCounter*nbrOfColumns) + i] + " ";
            
        }
        System.out.println(temp);
        rowCounter++;
        if(rowCounter == maxRow)
        {
            rowCounter = 0;
        }
    }

    public void resetRowCounter()
    {
        rowCounter = 0;
    }

    public void test()
    {
        for(int i = 0; i < data.length; i++)
        {
            System.out.println(data[i]);
        }
    }
}
