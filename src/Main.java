import controller.CalculatorController;
import view.CalculatorView;

public class Main {
    public static void main(String[] args) {
        var testView = new CalculatorView();
        var testController = new CalculatorController(testView);
    }
}
