package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;

public class CodeGenerator {
	public static void main(String[] args) throws IOException{
		
		
	}
	public static void gen(IExampleStatemachine s, EList<Event> e, EList<Property> p)
	{
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;");
		System.out.println("import java.io.BufferedReader;");
		System.out.println("import java.io.DataInputStream;");
		System.out.println("import java.io.IOException;");
		System.out.println("import java.io.InputStreamReader;");
		System.out.println("import java.util.Scanner;");
		System.out.println("import hu.bme.mit.yakindu.analysis.RuntimeService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.TimerService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;");
		System.out.println("public class RunStatechart {\n" + 
				"	\n" + 
				"	public static void main(String[] args) throws IOException {\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\n" + 
				"		s.setTimer(new TimerService());\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\n" + 
				"		s.init();\n" + 
				"		s.enter();\n" + 
				"		s.runCycle();\n" + 
				"		print(s);\n" + 
				"		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));\n"+
				"		String str=\"\";\n"+
				"while(str!=\"exit\")\n" + 
				"		{\n" + 
				"			str=buffer.readLine();\n"+
				"		System.out.println(str);");
		for(int i=0; i<e.size();i++)
		{
			String name=e.get(i).getName();
			System.out.println("if(str.compareTo("+name+")==0)\n" + 
					"			{\n" + 
					"				s.raise"+name+"();\n" + 
					"				System.out.println("+name+"\"raised\");\n" + 
					"			}");
		}
		System.out.println("print(s); \n } \n System.exit(0); \n } \n");
		System.out.println("public static void print(IExampleStatemachine s) {\n" + 
				"		System.out.println(\"W = \" + s.getSCInterface().getWhiteTime());\n" + 
				"		System.out.println(\"B = \" + s.getSCInterface().getBlackTime());\n" + 
				"	}");
		
	}
	
}
