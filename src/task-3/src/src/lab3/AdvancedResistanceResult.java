package lab3;

import java.io.Serializable;

public class AdvancedResistanceResult extends ResistanceResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public AdvancedResistanceResult() {
        super();
    }

    public AdvancedResistanceResult(double current, double voltage1, double voltage2, double voltage3) {
        super(current, voltage1, voltage2, voltage3);
    }
}
