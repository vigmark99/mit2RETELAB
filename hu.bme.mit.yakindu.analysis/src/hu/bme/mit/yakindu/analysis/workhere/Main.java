package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Direction;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		class structure{
			public structure(State state_) {
				b=false;
				state=state_;
			}
			public boolean b; //van-e belőle kiinduló nem önmagába futó tranzakció
			public State state;
		};
		// Reading model
		Statechart s = (Statechart) root;
		Scope scope=s.getScopes().get(0);
		System.out.println("bemenő események: ");
		EList<Event> al=scope.getEvents();
		for(int i=0; i<al.size(); i++)
		{
			Event event=al.get(i);
			if(event.getDirection()==Direction.IN) {
				System.out.println(event.getName());
			}
		}
		System.out.println("belső változók: ");
		EList<Property> al2=scope.getVariables();
		for(int i=0; i<al2.size(); i++)
		{
			Property p=al2.get(i);
			System.out.println(p.getName());
		}
		TreeIterator<EObject> iterator = s.eAllContents();
		ArrayList<structure> a= new ArrayList<structure>(); //itt tárolom a stateket és a hozzájuk tartozozó boolean értékeket
		ArrayList<String> str= new ArrayList<String>();
		str.add("Blue"); str.add("Blue"); str.add("Red"); str.add("Green"); str.add("AlkalmasNev"); str.add("Yellow");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				
				a.add(new structure(state));//ha találunk egy állapotot akkor betesszük
				if(state.getName().isEmpty())
				{
					System.out.print("Egy állapotnak nincs neve. Javasolt név:");
					
					
					int rnd = new Random().nextInt(str.size());
					String selected=str.get(rnd);
					state.setName(selected);
					System.out.println(selected);
					str.remove(rnd);
					
				}
				else
				{
					System.out.println(state.getName());
				}
			}
			if(content instanceof Transition) {
				Transition transiton = (Transition) content;
				System.out.print(transiton.getSource().getName());
				System.out.print(" -> ");
				System.out.println(transiton.getTarget().getName());
				if(transiton.getSource().getName()!=transiton.getTarget().getName())//itt megnézem, hogy a tranzakció forrása, különb-e a célnál
				{
					for(int i=0; i<a.size(); i++)
					{
						if(a.get(i).state.getName()==transiton.getSource().getName())//a megfelelő állapotot ha megtaláltuk
						{
							a.get(i).b=true;//akkor volt belőle kiinduló nem önmagába futó állapot
						}
					}
				}
			}
		}
		System.out.println("Nyelők:");
		for(int i=0; i<a.size(); i++) {
			if(!a.get(i).b)
			{
				System.out.println(a.get(i).state.getName());
			}
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
