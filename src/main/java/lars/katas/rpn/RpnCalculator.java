package lars.katas.rpn;

import java.math.BigDecimal;

public class RpnCalculator {
    private BigDecimal accumulator = new BigDecimal(0);

    public BigDecimal getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(BigDecimal value) {
        this.accumulator = value;
    }
}
