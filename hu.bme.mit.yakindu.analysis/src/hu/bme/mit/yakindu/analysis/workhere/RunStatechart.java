package hu.bme.mit.yakindu.analysis.workhere;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		print(s);
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String str="";
while(str!="exit")
		{
			str=buffer.readLine();
		System.out.println(str);
if(str.compareTo("start")==0)
			{
				s.raiseStart();
				System.out.println("start raised");
			}
if(str.compareTo("white")==0)
			{
				s.raiseWhite();
				System.out.println("white raised");
			}
if(str.compareTo("notblack")==0)
			{
				s.raiseNotblack();
				System.out.println("notblack raised");
			}
print(s); 
 } 
 System.exit(0); 
 } 

public static void print(IExampleStatemachine s) {

		System.out.println("W = " + s.getSCInterface().getWhiteTime());

		System.out.println("B = " + s.getSCInterface().getBlackTime());

}}
