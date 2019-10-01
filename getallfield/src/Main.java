import soot.G;
import soot.PackManager;
import soot.Transform;
import soot.options.Options;

public class Main 
{
	
	public static void main(String[] args)
	{
		String appPath = args[0];
		String androidJars = args[1];
		
		String[] arguments =
        {
            "-process-dir", appPath,
            "-android-jars", androidJars,
            "-ire",
            "-pp",
            "-allow-phantom-refs",
            "-w",
			"-p", "cg", "enabled:false"
        };
		
		G.reset();
		
		FieldFinder transformer = new FieldFinder();
		
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_output_format(Options.output_format_none);
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.MethodFeatureTransformer", transformer));
		
        soot.Main.main(arguments);
	}
	
	
}
