package utils.tradingProcess.strats;

/**
 * Class which is used to compare sets of values with given operators
 *
 * @author Jun Shao, Anthony Tam
 * @since 2022-04-01
 */
public class Compare {
    /**
     * Method which compares two coins with one operand
     *
     * @param left is the value on the left of the operand
     * @param right is the value on the right of the operand
     * @param operation is the operand used to compare values
     * @return boolean representing whether the trade conditions are successful
     */
    protected boolean compare(double left, double right, String operation) {
        if (operation.equals("<")) {
            return left < right;
        } else {
            return left > right;
        }
    }

    /**
     * Method which compares a set of 4 coins with 2 operands
     *
     * @param left is the value on the left of the first operand
     * @param right is the value on the right of the first operand
     * @param left2 is the value on the left of the second operand
     * @param right2 is the value on the right of the second operand
     * @param op1 is the first operand
     * @param op2 is the second operand
     * @return boolean representing whether a trade is successful
     */
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
