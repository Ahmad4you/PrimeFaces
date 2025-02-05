package ahmad.carousel;

import java.io.Serializable;

/**
 * 
 * @author Ahmad Alrefai
 */

public class ResponsiveOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String breakpoint;
    private int numVisible;
    private int numScroll;

    public ResponsiveOption(String breakpoint, int numVisible, int numScroll) {
        this.breakpoint = breakpoint;
        this.numVisible = numVisible;
        this.numScroll = numScroll;
    }

    // Getter und Setter
    public String getBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(String breakpoint) {
        this.breakpoint = breakpoint;
    }

    public int getNumVisible() {
        return numVisible;
    }

    public void setNumVisible(int numVisible) {
        this.numVisible = numVisible;
    }

    public int getNumScroll() {
        return numScroll;
    }

    public void setNumScroll(int numScroll) {
        this.numScroll = numScroll;
    }

    @Override
    public String toString() {
        return "ResponsiveOption{" +
                "breakpoint='" + breakpoint + '\'' +
                ", numVisible=" + numVisible +
                ", numScroll=" + numScroll +
                '}';
    }
}