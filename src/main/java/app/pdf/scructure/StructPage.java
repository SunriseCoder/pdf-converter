package app.pdf.scructure;

import java.util.List;

public class StructPage {
    private List<AbstractStructOperator> structList;

    public StructPage(List<AbstractStructOperator> structList) {
        this.structList = structList;
    }

    public List<AbstractStructOperator> getStructList() {
        return structList;
    }
}
