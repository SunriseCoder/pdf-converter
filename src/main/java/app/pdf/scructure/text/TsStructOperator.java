package app.pdf.scructure.text;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class TsStructOperator extends AbstractStructOperator {

    public TsStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }

    @Override
    public String generateOutput() throws IOException {
        return "<br />";
    }
}
