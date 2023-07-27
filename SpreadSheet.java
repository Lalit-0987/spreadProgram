import java.util.HashMap;
import java.util.Map;

public class SpreadSheet {
    private Map<String, Object> cellValues;

    public SpreadSheet() {
        cellValues = new HashMap<>();
    }

    public void setCellValue(String cellId, Object value) {
        cellValues.put(cellId, value);
    }

    public int getCellValue(String cellId) {
        Object value = cellValues.get(cellId);
        if (value == null) {
            return 0; // Default value if cell is not set
        } else if (value instanceof Integer) {
            return (int) value; // Return the value if it's a constant
        } else if (value instanceof String) {
            return evaluateFormula((String) value); // Evaluate the formula and return the result
        } else {
            throw new IllegalArgumentException("Invalid cell value type");
        }
    }

    private int evaluateFormula(String formula) {
        String[] tokens = formula.split("[+]");
        int result = 0;
        for (String token : tokens) {
            token = token.trim();
            if (token.startsWith("=")) {
                String cellId = token.substring(1).trim();
                result += getCellValue(cellId);
            } else {
                try {
                    result += (getCellValue(token));
                   // System.out.println("T2:"+);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid formula: " + formula);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SpreadSheet spreadsheet = new SpreadSheet();
        spreadsheet.setCellValue("A1", 13);
        spreadsheet.setCellValue("A2", 14);

        System.out.println(spreadsheet.getCellValue("A1")); // Output: 13
        System.out.println(spreadsheet.getCellValue("A2")); // Output: 14

        spreadsheet.setCellValue("A3", "=A1+A2");
        System.out.println(spreadsheet.getCellValue("A3")); // Output: 27

        spreadsheet.setCellValue("A4", "=A1+A2+A3");
        System.out.println(spreadsheet.getCellValue("A4")); // Output: 54
    }

}
