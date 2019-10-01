import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.*;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SourceLocator;
import soot.options.Options;

public class helloSoot {
    private String apiPath = "C:\\phd-work\\soot-work\\test";

    public void getClassUnderDir() {
    	LinkedHashSet<String> apiClasses = new LinkedHashSet<String>();
        for (String clzName: SourceLocator.v().getClassesUnder(apiPath)) {
            System.out.printf("api class: %s\n", clzName);
            //加载要处理的类设置为应用类，并加载到soot环境Scene中  
            Scene.v().loadClass(clzName, SootClass.BODIES).setApplicationClass();
        }
    }

    public void getMethods() {
        for (SootClass clz : Scene.v().getApplicationClasses()) {
            System.out.println(clz.getName());
            if (clz.getMethods().size() == 0){System.out.println("do not have methods!!!!!");}
            else{
                System.out.println("method num:"+clz.getMethods().size());
                for(SootMethod me : clz.getMethods()) {
                    System.out.println(me.toString());
                    if (me.hasActiveBody()){
                        System.out.println(me.getActiveBody().toString());
                    }
                }
            }

        }

    }
		
    private static void setOptions() {
         soot.options.Options.v().set_keep_line_number(true);
         soot.options.Options.v().set_whole_program(true);
       // LWG
         soot.options.Options.v().setPhaseOption("jb", "use-original-names:true");
         soot.options.Options.v().setPhaseOption("cg", "verbose:false");
         soot.options.Options.v().setPhaseOption("cg", "trim-clinit:true");
         //soot.options.Options.v().setPhaseOption("jb.tr", "ignore-wrong-staticness:true");

         soot.options.Options.v().set_src_prec(Options.src_prec_java);
         soot.options.Options.v().set_prepend_classpath(true);

         // don't optimize the program 
         soot.options.Options.v().setPhaseOption("wjop", "enabled:false");
         // allow for the absence of some classes
         //soot.options.Options.v().set_allow_phantom_refs(true);

    }

    private static void setSootClassPath() {
        StringBuffer cp = new StringBuffer();
        cp.append(".");
        cp.append(File.pathSeparator + "C:\\phd-work\\soot-work\\test");

    cp.append(File.pathSeparator+"C:\\Program Files\\Java\\jre7\\lib\\rt.jar"+File.pathSeparator+"C:\\Program Files\\Java\\jre7\\lib\\jce.jar");
        System.setProperty("soot.class.path", cp.toString());
    }

    public static void main(String[] args) {
        setSootClassPath();
        setOptions();//设置soot的选项

        helloSoot s = new helloSoot();
        s.getClassUnderDir();
        s.getMethods();
    }
}
