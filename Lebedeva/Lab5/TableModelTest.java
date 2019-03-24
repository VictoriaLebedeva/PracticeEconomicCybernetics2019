import javax.swing.table.AbstractTableModel;

class TableModelTest extends AbstractTableModel {
    private String[] parameterNames;
    private String[] parameters;
    private String genre;

    public TableModelTest(String[] parameters, String[] parameterNames, String genre) {
        this.parameters = parameters;
        this.parameterNames = parameterNames;
        this.genre = genre;
    }

    @Override
    public int getRowCount() {
        return parameterNames.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return parameterNames[rowIndex];
        } else {
            if (rowIndex == 0) {
                return parameters[rowIndex];
            }
            return parameters[rowIndex];
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Film parameter";
        } else {
            return "Information";
        }
    }
}