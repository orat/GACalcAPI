package util.cga;

/**
 * Cayley-Table for the geometric product in matrix form.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACayleyTableGeometricProduct extends CGACayleyTable {

    private static CGACayleyTableGeometricProduct cayleyTableGP;

    CGACayleyTableGeometricProduct() {
        super(cgaTable);
    }

    public static CGACayleyTableGeometricProduct instance() {
        if (cayleyTableGP == null) {
            cayleyTableGP = new CGACayleyTableGeometricProduct();
        }
        return cayleyTableGP;
    }

    // um a * b zu rechnen muss ich a als Matrix darstellen "Matrixform von GP"
    // dann kann ich b als Zeilenvektor ansehen bzw. den spaltenvektor a transponieren
    // und die Matrix mit dem Zeilenvektor
    // multiplizieren, sodass wieder ein Spaltenvektor entsteht
    // cayley-table des geometrischen Produkts in der nicht-matrix Form
    private final static String[][] cgaTable_nm = new String[][]{
        {
            "1", "e1", "e2", "e3",
            "e4", "e5", "e12", "e13",
            "e14", "e15", "e23", "e24",
            "e25", "e34", "e35", "e45",
            "e123", "e124", "e125", "e134",
            "e135", "e145", "e234", "e235",
            "e245", "e345", "e1234", "e1235",
            "e1245", "e1345", "e2345", "e12345"
        },
        {
            "e1", "1", "e12", "e13",
            "e14", "e15", "e2", "e3",
            "e4", "e5", "e123", "e124",
            "e125", "e134", "e135", "e145",
            "e23", "e24", "e25", "e34",
            "e35", "e45", "e1234", "e1235",
            "e1245", "e1345", "e234", "e235",
            "e245", "e345", "e12345", "e2345"
        },
        {
            "e2", "-e12", "1", "e23",
            "e24", "e25", "-e1", "-e123",
            "-e124", "-e125", "e3", "e4",
            "e5", "e234", "e235", "e245",
            "-e13", "-e14", "-e15", "-e1234",
            "-e1235", "-e1245", "e34", "e35",
            "e45", "e2345", "-e134", "-e135",
            "-e145", "-e12345", "e345", "-e1345"
        },
        {
            "e3", "-e13", "-e23", "1",
            "e34", "e35", "e123", "-e1",
            "-e134", "-e135", "-e2", "-e234",
            "-e235", "e4", "e5", "e345",
            "e12", "e1234", "e1235", "-e14",
            "-e15", "-e1345", "-e24", "-e25",
            "-e2345", "e45", "e124", "e125",
            "e12345", "-e145", "-e245", "e1245"
        },
        {
            "e4", "-e14", "-e24", "-e34",
            "1", "e45", "e124", "e134",
            "-e1", "-e145", "e234", "-e2",
            "-e245", "-e3", "-e345", "e5",
            "-e1234", "e12", "e1245", "e13",
            "e1345", "-e15", "e23", "e2345",
            "-e25", "-e35", "-e123", "-e12345",
            "e125", "e135", "e235", "-e1235"
        },
        {
            "e5", "-e15", "-e25", "-e35",
            "-e45", "-1", "e125", "e135",
            "e145", "e1", "e235", "e245",
            "e2", "e345", "e3", "e4",
            "-e1235", "-e1245", "-e12", "-e1345",
            "-e13", "-e14", "-e2345", "-e23",
            "-e24", "-e34", "e12345", "e123",
            "e124", "e134", "e234", "-e1234"
        },
        {
            "e12", "-e2", "e1", "e123",
            "e124", "e125", "-1", "-e23",
            "-e24", "-e25", "e13", "e14",
            "e15", "e1234", "e1235", "e1245",
            "-e3", "-e4", "-e5", "-e234",
            "-e235", "-e245", "e134", "e135",
            "e145", "e12345", "-e34", "-e35",
            "-e45", "-e2345", "e1345", "-e345"
        },
        {
            "e13", "-e3", "-e123", "e1",
            "e134", "e135", "e23", "-1",
            "-e34", "-e35", "-e12", "-e1234",
            "-e1235", "e14", "e15", "e1345",
            "e2", "e234", "e235", "-e4",
            "-e5", "-e345", "-e124", "-e125",
            "-e12345", "e145", "e24", "e25",
            "e2345", "-e45", "-e1245", "e245"
        },
        {
            "e14", "-e4", "-e124", "-e134",
            "e1", "e145", "e24", "e34",
            "-1", "-e45", "e1234", "-e12",
            "-e1245", "-e13", "-e1345", "e15",
            "-e234", "e2", "e245", "e3",
            "e345", "-e5", "e123", "e12345",
            "-e125", "-e135", "-e23", "-e2345",
            "e25", "e35", "e1235", "-e235"
        },
        {
            "e15", "-e5", "-e125", "-e135",
            "-e145", "-e1", "e25", "e35",
            "e45", "1", "e1235", "e1245",
            "e12", "e1345", "e13", "e14",
            "-e235", "-e245", "-e2", "-e345",
            "-e3", "-e4", "-e12345", "-e123",
            "-e124", "-e134", "e2345", "e23",
            "e24", "e34", "e1234", "-e234"
        },
        {
            "e23", "e123", "-e3", "e2",
            "e234", "e235", "-e13", "e12",
            "e1234", "e1235", "-1", "-e34",
            "-e35", "e24", "e25", "e2345",
            "-e1", "-e134", "-e135", "e124",
            "e125", "e12345", "-e4", "-e5",
            "-e345", "e245", "-e14", "-e15",
            "-e1345", "e1245", "-e45", "-e145"
        },
        {
            "e24", "e124", "-e4", "-e234",
            "e2", "e245", "-e14", "-e1234",
            "e12", "e1245", "e34", "-1",
            "-e45", "-e23", "-e2345", "e25",
            "e134", "-e1", "-e145", "-e123",
            "-e12345", "e125", "e3", "e345",
            "-e5", "-e235", "e13", "e1345",
            "-e15", "-e1235", "e35", "e135"
        },
        {
            "e25", "e125", "-e5", "-e235",
            "-e245", "-e2", "-e15", "-e1235",
            "-e1245", "-e12", "e35", "e45",
            "1", "e2345", "e23", "e24",
            "e135", "e145", "e1", "e12345",
            "e123", "e124", "-e345", "-e3",
            "-e4", "-e234", "-e1345", "-e13",
            "-e14", "-e1234", "e34", "e134"
        },
        {
            "e34", "e134", "e234", "-e4",
            "e3", "e345", "e1234", "-e14",
            "e13", "e1345", "-e24", "e23",
            "e2345", "-1", "-e45", "e35",
            "-e124", "e123", "e12345", "-e1",
            "-e145", "e135", "-e2", "-e245",
            "e235", "-e5", "-e12", "-e1245",
            "e1235", "-e15", "-e25", "-e125"
        },
        {
            "e35", "e135", "e235", "-e5",
            "-e345", "-e3", "e1235", "-e15",
            "-e1345", "-e13", "-e25", "-e2345",
            "-e23", "e45", "1", "e34",
            "-e125", "-e12345", "-e123", "e145",
            "e1", "e134", "e245", "e2",
            "e234", "-e4", "e1245", "e12",
            "e1234", "-e14", "-e24", "-e124"
        },
        {
            "e45", "e145", "e245", "e345",
            "-e5", "-e4", "e1245", "e1345",
            "-e15", "-e14", "e2345", "-e25",
            "-e24", "-e35", "-e34", "1",
            "e12345", "-e125", "-e124", "-e135",
            "-e134", "e1", "-e235", "-e234",
            "e2", "e3", "-e1235", "-e1234",
            "e12", "e13", "e23", "e123"
        },
        {
            "e123", "e23", "-e13", "e12",
            "e1234", "e1235", "-e3", "e2",
            "e234", "e235", "-e1", "-e134",
            "-e135", "e124", "e125", "e12345",
            "-1", "-e34", "-e35", "e24",
            "e25", "e2345", "-e14", "-e15",
            "-e1345", "e1245", "-e4", "-e5",
            "-e345", "e245", "-e145", "-e45"
        },
        {
            "e124", "e24", "-e14", "-e1234",
            "e12", "e1245", "-e4", "-e234",
            "e2", "e245", "e134", "-e1",
            "-e145", "-e123", "-e12345", "e125",
            "e34", "-1", "-e45", "-e23",
            "-e2345", "e25", "e13", "e1345",
            "-e15", "-e1235", "e3", "e345",
            "-e5", "-e235", "e135", "e35"
        },
        {
            "e125", "e25", "-e15", "-e1235",
            "-e1245", "-e12", "-e5", "-e235",
            "-e245", "-e2", "e135", "e145",
            "e1", "e12345", "e123", "e124",
            "e35", "e45", "1", "e2345",
            "e23", "e24", "-e1345", "-e13",
            "-e14", "-e1234", "-e345", "-e3",
            "-e4", "-e234", "e134", "e34"
        },
        {
            "e134", "e34", "e1234", "-e14",
            "e13", "e1345", "e234", "-e4",
            "e3", "e345", "-e124", "e123",
            "e12345", "-e1", "-e145", "e135",
            "-e24", "e23", "e2345", "-1",
            "-e45", "e35", "-e12", "-e1245",
            "e1235", "-e15", "-e2", "-e245",
            "e235", "-e5", "-e125", "-e25"
        },
        {
            "e135", "e35", "e1235", "-e15",
            "-e1345", "-e13", "e235", "-e5",
            "-e345", "-e3", "-e125", "-e12345",
            "-e123", "e145", "e1", "e134",
            "-e25", "-e2345", "-e23", "e45",
            "1", "e34", "e1245", "e12",
            "e1234", "-e14", "e245", "e2",
            "e234", "-e4", "-e124", "-e24"
        },
        {
            "e145", "e45", "e1245", "e1345",
            "-e15", "-e14", "e245", "e345",
            "-e5", "-e4", "e12345", "-e125",
            "-e124", "-e135", "-e134", "e1",
            "e2345", "-e25", "-e24", "-e35",
            "-e34", "1", "-e1235", "-e1234",
            "e12", "e13", "-e235", "-e234",
            "e2", "e3", "e123", "e23"
        },
        {
            "e234", "-e1234", "e34", "-e24",
            "e23", "e2345", "-e134", "e124",
            "-e123", "-e12345", "-e4", "e3",
            "e345", "-e2", "-e245", "e235",
            "e14", "-e13", "-e1345", "e12",
            "e1245", "-e1235", "-1", "-e45",
            "e35", "-e25", "e1", "e145",
            "-e135", "e125", "-e5", "e15"
        },
        {
            "e235", "-e1235", "e35", "-e25",
            "-e2345", "-e23", "-e135", "e125",
            "e12345", "e123", "-e5", "-e345",
            "-e3", "e245", "e2", "e234",
            "e15", "e1345", "e13", "-e1245",
            "-e12", "-e1234", "e45", "1",
            "e34", "-e24", "-e145", "-e1",
            "-e134", "e124", "-e4", "e14"
        },
        {
            "e245", "-e1245", "e45", "e2345",
            "-e25", "-e24", "-e145", "-e12345",
            "e125", "e124", "e345", "-e5",
            "-e4", "-e235", "-e234", "e2",
            "-e1345", "e15", "e14", "e1235",
            "e1234", "-e12", "-e35", "-e34",
            "1", "e23", "e135", "e134",
            "-e1", "-e123", "e3", "-e13"
        },
        {
            "e345", "-e1345", "-e2345", "e45",
            "-e35", "-e34", "e12345", "-e145",
            "e135", "e134", "-e245", "e235",
            "e234", "-e5", "-e4", "e3",
            "e1245", "-e1235", "-e1234", "e15",
            "e14", "-e13", "e25", "e24",
            "-e23", "1", "-e125", "-e124",
            "e123", "-e1", "-e2", "e12"
        },
        {
            "e1234", "-e234", "e134", "-e124",
            "e123", "e12345", "-e34", "e24",
            "-e23", "-e2345", "-e14", "e13",
            "e1345", "-e12", "-e1245", "e1235",
            "e4", "-e3", "-e345", "e2",
            "e245", "-e235", "-e1", "-e145",
            "e135", "-e125", "1", "e45",
            "-e35", "e25", "-e15", "e5"
        },
        {
            "e1235", "-e235", "e135", "-e125",
            "-e12345", "-e123", "-e35", "e25",
            "e2345", "e23", "-e15", "-e1345",
            "-e13", "e1245", "e12", "e1234",
            "e5", "e345", "e3", "-e245",
            "-e2", "-e234", "e145", "e1",
            "e134", "-e124", "-e45", "-1",
            "-e34", "e24", "-e14", "e4"
        },
        {
            "e1245", "-e245", "e145", "e12345",
            "-e125", "-e124", "-e45", "-e2345",
            "e25", "e24", "e1345", "-e15",
            "-e14", "-e1235", "-e1234", "e12",
            "-e345", "e5", "e4", "e235",
            "e234", "-e2", "-e135", "-e134",
            "e1", "e123", "e35", "e34",
            "-1", "-e23", "e13", "-e3"
        },
        {
            "e1345", "-e345", "-e12345", "e145",
            "-e135", "-e134", "e2345", "-e45",
            "e35", "e34", "-e1245", "e1235",
            "e1234", "-e15", "-e14", "e13",
            "e245", "-e235", "-e234", "e5",
            "e4", "-e3", "e125", "e124",
            "-e123", "e1", "-e25", "-e24",
            "e23", "-1", "-e12", "e2"
        },
        {
            "e2345", "e12345", "-e345", "e245",
            "-e235", "-e234", "-e1345", "e1245",
            "-e1235", "-e1234", "-e45", "e35",
            "e34", "-e25", "-e24", "e23",
            "-e145", "e135", "e134", "-e125",
            "-e124", "e123", "e5", "e4",
            "-e3", "e2", "e15", "e14",
            "-e13", "e12", "-1", "-e1"
        },
        {
            "e12345", "e2345", "-e1345", "e1245",
            "-e1235", "-e1234", "-e345", "e245",
            "-e235", "-e234", "-e145", "e135",
            "e134", "-e125", "-e124", "e123",
            "-e45", "e35", "e34", "-e25",
            "-e24", "e23", "e15", "e14",
            "-e13", "e12", "e5", "e4",
            "-e3", "e2", "-e1", "-1"
        }
    };

    private final static String[][] cgaTable = new String[][]{
        {
            "1", "e1", "e2", "e3",
            "e4", "e5", "e12", "e13",
            "e14", "e15", "e23", "e24",
            "e25", "e34", "e35", "e45",
            "e123", "e124", "e125", "e134",
            "e135", "e145", "e234", "e235",
            "e245", "e345", "e1234", "e1235",
            "e1245", "e1345", "e2345", "e12345"
        },
        {
            "e1", "1", "e12", "e13",
            "e14", "e15", "e2", "e3",
            "e4", "e5", "e123", "e124",
            "e125", "e134", "e135", "e145",
            "e23", "e24", "e25", "e34",
            "e35", "e45", "e1234", "e1235",
            "e1245", "e1345", "e234", "e235",
            "e245", "e345", "e12345", "e2345"
        },
        {
            "e2", "-e12", "1", "e23",
            "e24", "e25", "-e1", "-e123",
            "-e124", "-e125", "e3", "e4",
            "e5", "e234", "e235", "e245",
            "-e13", "-e14", "-e15", "-e1234",
            "-e1235", "-e1245", "e34", "e35",
            "e45", "e2345", "-e134", "-e135",
            "-e145", "-e12345", "e345", "-e1345"
        },
        {
            "e3", "-e13", "-e23", "1",
            "e34", "e35", "e123", "-e1",
            "-e134", "-e135", "-e2", "-e234",
            "-e235", "e4", "e5", "e345",
            "e12", "e1234", "e1235", "-e14",
            "-e15", "-e1345", "-e24", "-e25",
            "-e2345", "e45", "e124", "e125",
            "e12345", "-e145", "-e245", "e1245"
        },
        {
            "e4", "-e14", "-e24", "-e34",
            "1", "e45", "e124", "e134",
            "-e1", "-e145", "e234", "-e2",
            "-e245", "-e3", "-e345", "e5",
            "-e1234", "e12", "e1245", "e13",
            "e1345", "-e15", "e23", "e2345",
            "-e25", "-e35", "-e123", "-e12345",
            "e125", "e135", "e235", "-e1235"
        },
        {
            "-e5", "e15", "e25", "e35",
            "e45", "1", "-e125", "-e135",
            "-e145", "-e1", "-e235", "-e245",
            "-e2", "-e345", "-e3", "-e4",
            "e1235", "e1245", "e12", "e1345",
            "e13", "e14", "e2345", "e23",
            "e24", "e34", "-e12345", "-e123",
            "-e124", "-e134", "-e234", "e1234"
        },
        {
            "-e12", "e2", "-e1", "-e123",
            "-e124", "-e125", "1", "e23",
            "e24", "e25", "-e13", "-e14",
            "-e15", "-e1234", "-e1235", "-e1245",
            "e3", "e4", "e5", "e234",
            "e235", "e245", "-e134", "-e135",
            "-e145", "-e12345", "e34", "e35",
            "e45", "e2345", "-e1345", "e345"
        },
        {
            "-e13", "e3", "e123", "-e1",
            "-e134", "-e135", "-e23", "1",
            "e34", "e35", "e12", "e1234",
            "e1235", "-e14", "-e15", "-e1345",
            "-e2", "-e234", "-e235", "e4",
            "e5", "e345", "e124", "e125",
            "e12345", "-e145", "-e24", "-e25",
            "-e2345", "e45", "e1245", "-e245"
        },
        {
            "-e14", "e4", "e124", "e134",
            "-e1", "-e145", "-e24", "-e34",
            "1", "e45", "-e1234", "e12",
            "e1245", "e13", "e1345", "-e15",
            "e234", "-e2", "-e245", "-e3",
            "-e345", "e5", "-e123", "-e12345",
            "e125", "e135", "e23", "e2345",
            "-e25", "-e35", "-e1235", "e235"
        },
        {
            "e15", "-e5", "-e125", "-e135",
            "-e145", "-e1", "e25", "e35",
            "e45", "1", "e1235", "e1245",
            "e12", "e1345", "e13", "e14",
            "-e235", "-e245", "-e2", "-e345",
            "-e3", "-e4", "-e12345", "-e123",
            "-e124", "-e134", "e2345", "e23",
            "e24", "e34", "e1234", "-e234"
        },
        {
            "-e23", "-e123", "e3", "-e2",
            "-e234", "-e235", "e13", "-e12",
            "-e1234", "-e1235", "1", "e34",
            "e35", "-e24", "-e25", "-e2345",
            "e1", "e134", "e135", "-e124",
            "-e125", "-e12345", "e4", "e5",
            "e345", "-e245", "e14", "e15",
            "e1345", "-e1245", "e45", "e145"
        },
        {
            "-e24", "-e124", "e4", "e234",
            "-e2", "-e245", "e14", "e1234",
            "-e12", "-e1245", "-e34", "1",
            "e45", "e23", "e2345", "-e25",
            "-e134", "e1", "e145", "e123",
            "e12345", "-e125", "-e3", "-e345",
            "e5", "e235", "-e13", "-e1345",
            "e15", "e1235", "-e35", "-e135"
        },
        {
            "e25", "e125", "-e5", "-e235",
            "-e245", "-e2", "-e15", "-e1235",
            "-e1245", "-e12", "e35", "e45",
            "1", "e2345", "e23", "e24",
            "e135", "e145", "e1", "e12345",
            "e123", "e124", "-e345", "-e3",
            "-e4", "-e234", "-e1345", "-e13",
            "-e14", "-e1234", "e34", "e134"
        },
        {
            "-e34", "-e134", "-e234", "e4",
            "-e3", "-e345", "-e1234", "e14",
            "-e13", "-e1345", "e24", "-e23",
            "-e2345", "1", "e45", "-e35",
            "e124", "-e123", "-e12345", "e1",
            "e145", "-e135", "e2", "e245",
            "-e235", "e5", "e12", "e1245",
            "-e1235", "e15", "e25", "e125"
        },
        {
            "e35", "e135", "e235", "-e5",
            "-e345", "-e3", "e1235", "-e15",
            "-e1345", "-e13", "-e25", "-e2345",
            "-e23", "e45", "1", "e34",
            "-e125", "-e12345", "-e123", "e145",
            "e1", "e134", "e245", "e2",
            "e234", "-e4", "e1245", "e12",
            "e1234", "-e14", "-e24", "-e124"
        },
        {
            "e45", "e145", "e245", "e345",
            "-e5", "-e4", "e1245", "e1345",
            "-e15", "-e14", "e2345", "-e25",
            "-e24", "-e35", "-e34", "1",
            "e12345", "-e125", "-e124", "-e135",
            "-e134", "e1", "-e235", "-e234",
            "e2", "e3", "-e1235", "-e1234",
            "e12", "e13", "e23", "e123"
        },
        {
            "-e123", "-e23", "e13", "-e12",
            "-e1234", "-e1235", "e3", "-e2",
            "-e234", "-e235", "e1", "e134",
            "e135", "-e124", "-e125", "-e12345",
            "1", "e34", "e35", "-e24",
            "-e25", "-e2345", "e14", "e15",
            "e1345", "-e1245", "e4", "e5",
            "e345", "-e245", "e145", "e45"
        },
        {
            "-e124", "-e24", "e14", "e1234",
            "-e12", "-e1245", "e4", "e234",
            "-e2", "-e245", "-e134", "e1",
            "e145", "e123", "e12345", "-e125",
            "-e34", "1", "e45", "e23",
            "e2345", "-e25", "-e13", "-e1345",
            "e15", "e1235", "-e3", "-e345",
            "e5", "e235", "-e135", "-e35"
        },
        {
            "e125", "e25", "-e15", "-e1235",
            "-e1245", "-e12", "-e5", "-e235",
            "-e245", "-e2", "e135", "e145",
            "e1", "e12345", "e123", "e124",
            "e35", "e45", "1", "e2345",
            "e23", "e24", "-e1345", "-e13",
            "-e14", "-e1234", "-e345", "-e3",
            "-e4", "-e234", "e134", "e34"
        },
        {
            "-e134", "-e34", "-e1234", "e14",
            "-e13", "-e1345", "-e234", "e4",
            "-e3", "-e345", "e124", "-e123",
            "-e12345", "e1", "e145", "-e135",
            "e24", "-e23", "-e2345", "1",
            "e45", "-e35", "e12", "e1245",
            "-e1235", "e15", "e2", "e245",
            "-e235", "e5", "e125", "e25"
        },
        {
            "e135", "e35", "e1235", "-e15",
            "-e1345", "-e13", "e235", "-e5",
            "-e345", "-e3", "-e125", "-e12345",
            "-e123", "e145", "e1", "e134",
            "-e25", "-e2345", "-e23", "e45",
            "1", "e34", "e1245", "e12",
            "e1234", "-e14", "e245", "e2",
            "e234", "-e4", "-e124", "-e24"
        },
        {
            "e145", "e45", "e1245", "e1345",
            "-e15", "-e14", "e245", "e345",
            "-e5", "-e4", "e12345", "-e125",
            "-e124", "-e135", "-e134", "e1",
            "e2345", "-e25", "-e24", "-e35",
            "-e34", "1", "-e1235", "-e1234",
            "e12", "e13", "-e235", "-e234",
            "e2", "e3", "e123", "e23"
        },
        {
            "-e234", "e1234", "-e34", "e24",
            "-e23", "-e2345", "e134", "-e124",
            "e123", "e12345", "e4", "-e3",
            "-e345", "e2", "e245", "-e235",
            "-e14", "e13", "e1345", "-e12",
            "-e1245", "e1235", "1", "e45",
            "-e35", "e25", "-e1", "-e145",
            "e135", "-e125", "e5", "-e15"
        },
        {
            "e235", "-e1235", "e35", "-e25",
            "-e2345", "-e23", "-e135", "e125",
            "e12345", "e123", "-e5", "-e345",
            "-e3", "e245", "e2", "e234",
            "e15", "e1345", "e13", "-e1245",
            "-e12", "-e1234", "e45", "1",
            "e34", "-e24", "-e145", "-e1",
            "-e134", "e124", "-e4", "e14"
        },
        {
            "e245", "-e1245", "e45", "e2345",
            "-e25", "-e24", "-e145", "-e12345",
            "e125", "e124", "e345", "-e5",
            "-e4", "-e235", "-e234", "e2",
            "-e1345", "e15", "e14", "e1235",
            "e1234", "-e12", "-e35", "-e34",
            "1", "e23", "e135", "e134",
            "-e1", "-e123", "e3", "-e13"
        },
        {
            "e345", "-e1345", "-e2345", "e45",
            "-e35", "-e34", "e12345", "-e145",
            "e135", "e134", "-e245", "e235",
            "e234", "-e5", "-e4", "e3",
            "e1245", "-e1235", "-e1234", "e15",
            "e14", "-e13", "e25", "e24",
            "-e23", "1", "-e125", "-e124",
            "e123", "-e1", "-e2", "e12"
        },
        {
            "e1234", "-e234", "e134", "-e124",
            "e123", "e12345", "-e34", "e24",
            "-e23", "-e2345", "-e14", "e13",
            "e1345", "-e12", "-e1245", "e1235",
            "e4", "-e3", "-e345", "e2",
            "e245", "-e235", "-e1", "-e145",
            "e135", "-e125", "1", "e45",
            "-e35", "e25", "-e15", "e5"
        },
        {
            "-e1235", "e235", "-e135", "e125",
            "e12345", "e123", "e35", "-e25",
            "-e2345", "-e23", "e15", "e1345",
            "e13", "-e1245", "-e12", "-e1234",
            "-e5", "-e345", "-e3", "e245",
            "e2", "e234", "-e145", "-e1",
            "-e134", "e124", "e45", "1",
            "e34", "-e24", "e14", "-e4"
        },
        {
            "-e1245", "e245", "-e145", "-e12345",
            "e125", "e124", "e45", "e2345",
            "-e25", "-e24", "-e1345", "e15",
            "e14", "e1235", "e1234", "-e12",
            "e345", "-e5", "-e4", "-e235",
            "-e234", "e2", "e135", "e134",
            "-e1", "-e123", "-e35", "-e34",
            "1", "e23", "-e13", "e3"
        },
        {
            "-e1345", "e345", "e12345", "-e145",
            "e135", "e134", "-e2345", "e45",
            "-e35", "-e34", "e1245", "-e1235",
            "-e1234", "e15", "e14", "-e13",
            "-e245", "e235", "e234", "-e5",
            "-e4", "e3", "-e125", "-e124",
            "e123", "-e1", "e25", "e24",
            "-e23", "1", "e12", "-e2"
        },
        {
            "-e2345", "-e12345", "e345", "-e245",
            "e235", "e234", "e1345", "-e1245",
            "e1235", "e1234", "e45", "-e35",
            "-e34", "e25", "e24", "-e23",
            "e145", "-e135", "-e134", "e125",
            "e124", "-e123", "-e5", "-e4",
            "e3", "-e2", "-e15", "-e14",
            "e13", "-e12", "1", "e1"
        },
        {
            "-e12345", "-e2345", "e1345", "-e1245",
            "e1235", "e1234", "e345", "-e245",
            "e235", "e234", "e145", "-e135",
            "-e134", "e125", "e124", "-e123",
            "e45", "-e35", "-e34", "e25",
            "e24", "-e23", "-e15", "-e14",
            "e13", "-e12", "-e5", "-e4",
            "e3", "-e2", "e1", "1"
        }
    };

}
