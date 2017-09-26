package app.pdf.scructure.text;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class TfStructOperator extends AbstractStructOperator {

    public TfStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
