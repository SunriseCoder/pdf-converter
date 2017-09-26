package app.pdf.scructure;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.font.PDFont;

import app.pdf.PDFParser;
import app.pdf.scructure.text.BDCStructOperator;
import app.pdf.scructure.text.BTStructOperator;
import app.pdf.scructure.text.TD_StructOperator;
import app.pdf.scructure.text.TJ_StructOperator;
import app.pdf.scructure.text.TcStructOperator;
import app.pdf.scructure.text.TdStructOperator;
import app.pdf.scructure.text.TfStructOperator;
import app.pdf.scructure.text.TjStructOperator;
import app.pdf.scructure.text.TmStructOperator;
import app.pdf.scructure.text.TsStructOperator;
import app.pdf.scructure.text.TwStructOperator;

public class TextOperators extends AbstractOperatorGroup {

    @Override
    public boolean parseOperator(PDFParser parser, String operator) {
        List<COSBase> cosStack = parser.getCosStack();
        List<AbstractStructOperator> structList = parser.getStructList();
        final AbstractStructOperator currentStructOperator = parser.getCurrentStructOperator();

        PDFont font;
        switch (operator) {
        case "BDC": // Begin Marked Content
            AbstractStructOperator bdcStructOperator = new BDCStructOperator(cosStack);
            bdcStructOperator.setParent(currentStructOperator);
            if (currentStructOperator == null) {
                structList.add(bdcStructOperator);
            } else {
                currentStructOperator.addChild(bdcStructOperator);
            }
            parser.setCurrentStructOperator(bdcStructOperator);
            break;
        case "EMC": // End of Marked Content
            parser.setCurrentStructOperator(currentStructOperator.getParent());
            if (!cosStack.isEmpty()) {
                throw new IllegalStateException();
            }
            cosStack.clear();
            break;
        case "BT": // Begin Text
            BTStructOperator btStructOperator = new BTStructOperator(cosStack);
            btStructOperator.setParent(currentStructOperator);
            if (currentStructOperator == null) {
                structList.add(btStructOperator);
            } else {
                currentStructOperator.addChild(btStructOperator);
            }
            parser.setCurrentStructOperator(btStructOperator);
            break;
        case "ET": // End Text
            parser.setCurrentStructOperator(currentStructOperator.getParent());
            if (!cosStack.isEmpty()) {
                throw new IllegalStateException();
            }
            cosStack.clear();
            break;
        case "Tc": // Text character spacing
            AbstractStructOperator tcStructOperator = new TcStructOperator(cosStack);
            currentStructOperator.addChild(tcStructOperator);
            break;
        case "Tw": // Text word spacing
            AbstractStructOperator twStructOperator = new TwStructOperator(cosStack);
            currentStructOperator.addChild(twStructOperator);
            break;
        case "Td": // Text positioning
            AbstractStructOperator tdSturctOperator = new TdStructOperator(cosStack);
            currentStructOperator.addChild(tdSturctOperator);
            break;
        case "T*": // Next line
            AbstractStructOperator tsSturctOperator = new TsStructOperator(cosStack);
            currentStructOperator.addChild(tsSturctOperator);
            break;
        case "TD": // Text positioning with lead as text
            AbstractStructOperator tDSturctOperator = new TD_StructOperator(cosStack);
            currentStructOperator.addChild(tDSturctOperator);
            break;
        case "Tm": // Text positioning matrix
            AbstractStructOperator tmSturctOperator = new TmStructOperator(cosStack);
            currentStructOperator.addChild(tmSturctOperator);
            break;
        case "Tf": // Text font
            String fontKey = ((COSName) cosStack.get(0)).getName();
            AbstractStructOperator tfSturctOperator = new TfStructOperator(cosStack);
            font = parser.getFont(fontKey);
            parser.setCurrentFont(font);
            currentStructOperator.addChild(tfSturctOperator);
            break;
        case "Tj": // Text showing
            AbstractStructOperator tjSturctOperator = new TjStructOperator(cosStack);
            font = parser.getCurrentFont();
            tjSturctOperator.setFont(font);
            currentStructOperator.addChild(tjSturctOperator);
            break;
        case "TJ": // Text showing (Array)
            AbstractStructOperator tJSturctOperator = new TJ_StructOperator(cosStack);
            font = parser.getCurrentFont();
            tJSturctOperator.setFont(font);
            currentStructOperator.addChild(tJSturctOperator);
            break;
        default:
            return false;
        }

        return true;
    }
}
