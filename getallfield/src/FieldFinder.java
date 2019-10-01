import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.PatchingChain;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.SootFieldRef;
import soot.SootField;
import soot.Unit;
import soot.jimple.Stmt;
import soot.util.Chain;

public class FieldFinder extends SceneTransformer
{

	/**
	 * Find all fields used in apk
	 */
	@Override
	protected void internalTransform(String phaseName, Map<String, String> options) 
	{
		//(1) Obtain all application classes
		Chain<SootClass> sootClasses = Scene.v().getClasses();
		
		for (Iterator<SootClass> iter = sootClasses.snapshotIterator(); iter.hasNext();)
		{
			SootClass sc = iter.next();
			
			//(2) Obtain all the fields from a given class
			Chain<SootField> sootFields = sc.getFields();
		
		
		
		    //(3) Print out all the fields related to sensor service
			for (int j = 0; j < sootFields.size(); j++)
			{
					SootField sf = sootFields.iterator().next();
					
				
					SootClass calleeClass_f = sf.getDeclaringClass();
					String calleeClassName_f = calleeClass_f.getName();
					if (calleeClassName_f.startsWith("android.hardware.Sensor"))
					{
					System.out.println(sf.getSignature());
					}
												
			}
									
		}
	}
}

