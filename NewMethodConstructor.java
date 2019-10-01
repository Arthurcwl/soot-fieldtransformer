package edu.monash.sootbeginner;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.droidra.booster.InstrumentationUtils;
import soot.ArrayType;
import soot.Body;
import soot.IntType;
import soot.Local;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.javaToJimple.LocalGenerator;
import soot.jimple.AssignStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.android.iccta.ICCDummyMainCreator;
import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;
public class NewMethodConstructor extends SceneTransformer
{

	/**
	 * Print Android APIs (starting with com.android)
	 */
	@Override
	protected void internalTransform(String phaseName, Map<String, String> options) 
	{
		//(1) Obtain all application classes
		Chain<SootClass> sootClasses = Scene.v().getClasses();
		
		for (Iterator<SootClass> iter = sootClasses.snapshotIterator(); iter.hasNext();)
		{
			SootClass sc = iter.next();
			
			if(sc.getName() == "android.hardware.SensorEvent")
			{
				SootMethod newSM = new SootMethod("getvalues", 
						Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.Float"), 1)}), 
		    			VoidType.v(), 
		    			Modifier.PUBLIC | Modifier.STATIC);
				
				JimpleBody body = Jimple.v().newBody(newSM);
		    	newSM.setActiveBody(body);
				
		        sc.addMethod(newSM);
		        JimpleBody body = Jimple.v().newBody(newSM);
		        newSM.setActiveBody(body);
		        
		        
		        Local al = generator.generateLocal(cls.getType());
				
				Unit newU = (Unit) Jimple.v().newAssignStmt(al, Jimple.v().newNewExpr(cls.getType()));
				
				Unit initU = (Unit) Jimple.v().newInvokeStmt(
						Jimple.v().newSpecialInvokeExpr(al, sootMethod.makeRef()));
				
				Unit callU = (Unit) Jimple.v().newInvokeStmt(
						Jimple.v().newSpecialInvokeExpr(al, method.makeRef()));
				
				body.getUnits().add(newU);
				body.getUnits().add(initU);
				body.getUnits().add(callU);
			}
			
			//(2) Obtain all the fields from a given class
			Chain<SootField> sootFields = sc.getFields();
			
			
		    
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

