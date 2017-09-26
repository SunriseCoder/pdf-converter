package app.pdf.scructure;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.PDFParser;
import app.pdf.scructure.graphics.CS_StructOperator;
import app.pdf.scructure.graphics.CStructOperator;
import app.pdf.scructure.graphics.CmStructOperator;
import app.pdf.scructure.graphics.CsStructOperator;
import app.pdf.scructure.graphics.FStructOperator;
import app.pdf.scructure.graphics.FsStructOperator;
import app.pdf.scructure.graphics.HStructOperator;
import app.pdf.scructure.graphics.JStructOperator;
import app.pdf.scructure.graphics.J_StructOperator;
import app.pdf.scructure.graphics.MStructOperator;
import app.pdf.scructure.graphics.NStructOperator;
import app.pdf.scructure.graphics.QStructOperator;
import app.pdf.scructure.graphics.Q_StructOperator;
import app.pdf.scructure.graphics.ReStructOperator;
import app.pdf.scructure.graphics.SCN_StructOperator;
import app.pdf.scructure.graphics.S_StructOperator;
import app.pdf.scructure.graphics.ScnStructOperator;
import app.pdf.scructure.graphics.WStructOperator;
import app.pdf.scructure.graphics.W_StructOperator;

public class GraphicOperators extends AbstractOperatorGroup {

    @Override
    public boolean parseOperator(PDFParser parser, String operator) {
        List<COSBase> cosStack = parser.getCosStack();
        List<AbstractStructOperator> structList = parser.getStructList();
        AbstractStructOperator currentStructOperator = parser.getCurrentStructOperator();

        switch (operator) {
        case "c" : // Append cubic Bezier curve
            AbstractStructOperator cStructOperator = new CStructOperator(cosStack);
            currentStructOperator.addChild(cStructOperator);
            break;
        case "CS": // Color Space (stroke)
            AbstractStructOperator cSStructOperator = new CS_StructOperator(cosStack);
            currentStructOperator.addChild(cSStructOperator);
            break;
        case "cs": // Color Space (non-stroke)
            AbstractStructOperator csStructOperator = new CsStructOperator(cosStack);
            currentStructOperator.addChild(csStructOperator);
            break;
        case "cm": // Current transformation matrix
            AbstractStructOperator cmStructOperator = new CmStructOperator(cosStack);
            currentStructOperator.addChild(cmStructOperator);
            break;
        case "f": // Fill the path (non-zero)
            AbstractStructOperator fSturctOperator = new FStructOperator(cosStack);
            if (currentStructOperator == null) {
                structList.add(fSturctOperator);
            } else {
                currentStructOperator.addChild(fSturctOperator);
            }
            break;
        case "f*": // Fill the path (even-odd)
            AbstractStructOperator fsSturctOperator = new FsStructOperator(cosStack);
            currentStructOperator.addChild(fsSturctOperator);
            break;
        case "h": // Start new subpath
            AbstractStructOperator hStructOperator = new HStructOperator(cosStack);
            currentStructOperator.addChild(hStructOperator);
            break;
        case "J": // Line cap style
            AbstractStructOperator JStructOperator = new J_StructOperator(cosStack);
            currentStructOperator.addChild(JStructOperator);
            break;
        case "j": // Line join style
            AbstractStructOperator jStructOperator = new JStructOperator(cosStack);
            currentStructOperator.addChild(jStructOperator);
            break;
        case "m": // Begin new subpath
            AbstractStructOperator mStructOperator = new MStructOperator(cosStack);
            currentStructOperator.addChild(mStructOperator);
            break;
        case "n": // End of path object without filling or stroke out
            AbstractStructOperator nStructOperator = new NStructOperator(cosStack);
            currentStructOperator.addChild(nStructOperator);
            break;
        case "q": // Save whole graphics state to stack
            AbstractStructOperator qStructOperator = new QStructOperator(cosStack);
            currentStructOperator.addChild(qStructOperator);
            break;
        case "Q": // Restore whole graphics state from stack
            AbstractStructOperator QStructOperator = new Q_StructOperator(cosStack);
            if (currentStructOperator == null) {
                structList.add(QStructOperator);
            } else {
                currentStructOperator.addChild(QStructOperator);
            }
            break;
        case "re": // Path Rectangle
            AbstractStructOperator reSturctOperator = new ReStructOperator(cosStack);
            if (currentStructOperator == null) {
                structList.add(reSturctOperator);
            } else {
                currentStructOperator.addChild(reSturctOperator);
            }
            break;
        case "S": // Stroke the path
            AbstractStructOperator sStructOperator = new S_StructOperator(cosStack);
            currentStructOperator.addChild(sStructOperator);
            break;
        case "SCN": // Set Color (stroke)
            AbstractStructOperator sCNStructOperator = new SCN_StructOperator(cosStack);
            currentStructOperator.addChild(sCNStructOperator);
            break;
        case "scn": // Set Color (non-stroke)
            AbstractStructOperator scnStructOperator = new ScnStructOperator(cosStack);
            currentStructOperator.addChild(scnStructOperator);
            break;
        case "W": // Clipping path (non-zero)
            AbstractStructOperator WStructOperator = new W_StructOperator(cosStack);
            currentStructOperator.addChild(WStructOperator);
            break;
        case "w": // Line width
            AbstractStructOperator wStructOperator = new WStructOperator(cosStack);
            currentStructOperator.addChild(wStructOperator);
            break;
        case "B*": // Fill and then stroke the path (even-odd rule)
        case "Do": // Paint XObject (for examle, image)
        case "gs": // Graphics state special parameters
        case "l": // Append a straight line segment
            AbstractStructOperator unsupportedStructOperator = new UnsupportedStructOperator(cosStack);
            currentStructOperator.addChild(unsupportedStructOperator);
            break;
        default:
            return false;
        }

        return true;
    }
}
