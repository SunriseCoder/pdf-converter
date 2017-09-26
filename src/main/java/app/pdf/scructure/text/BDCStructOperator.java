package app.pdf.scructure.text;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class BDCStructOperator extends AbstractStructOperator {

    public BDCStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }

    @Override
    public String generateOutput() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (AbstractStructOperator child : children) {
            String childOutput = child.generateOutput();
            sb.append(childOutput).append("\n");
        }
        return sb.toString();
    }
}
