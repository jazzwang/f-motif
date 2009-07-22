package fmotif;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class querydata {
    String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
    String DATABASE_URL = "jdbc:odbc:project3";
    String DATABASE_TABLE = "phosphoELM_1208";//"Dataset_041106"//"Dataset_0707"//"phosphoELM_1208"
    String kinasesname[]={"PKA","PKC","CK2","CDK","Src","ATM","CaM-KII","MAPK","4 Kinases"};//_group
    //String testkinasesname[]={"PKA_group","PKC_group","CK2_group","CDK_group","Src_group"};
    String codenames[]={"S","T","Y","_"};
    String speciesnames[] ={"Homo sapiens","Mus musculus","Rattus norvegicus","ALL"};
    String formname[] = {"forsvm","fornn"};
    String formatnames[] = {"","Pssm_","ExtPssm_"};
    String formatname;
    String species;
    String form;
    String kinease;
    String notkinease;
    String code;
    int windows_size;
    int slide_size;
    int kfold;
    int part;
    int choice;
    String shift;
    public querydata() {
        choice =0;
        kinease = kinasesname[choice];
        //notkinease = testkinasesname[choice];
        species = speciesnames[0];
        code = codenames[0];
        form = formname[0];
        formatname = formatnames[0];
        //code = "S' OR code LIKE 'T' OR code LIKE 'Y";
        windows_size = 13;
        slide_size = 13;
        shift = "";
        kfold = 7;
        part = 0;


    }

}
