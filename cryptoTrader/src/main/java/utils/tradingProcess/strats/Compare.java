package utils.tradingProcess.strats;

public class Compare {
    protected boolean compare(double left, double right, String operation) {
        if (operation.equals("<")) {
            return left < right;
        } else {
            return left > right;
        }
    }

    protected boolean compare(double left, double right, double left2, double right2, String op1, String op2) {
        boolean result = false;
        if (op1.equals("<")) {
            if (op2.equals("<")) {
                result = left < right && left2 < right2;
            } else {
                result = left < right && left2 > right2;
            }
        } else {
            if (op2.equals(">")) {
                result = left > right && left2 > right2;
            } else {
                result = left > right && left2 < right2;
            }
        }
        return result;
    }
}
