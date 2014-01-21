package Backend;

import java.util.List;

import Managers.Control;
import Models.Ingredient;
import Models.Instruction;
import Models.Recipe;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@SuppressWarnings("unused")
public class HardwareControl {
/*
	final GpioController gpio;
	final GpioPinDigitalOutput pin1;
	final GpioPinDigitalOutput pin2;
	final GpioPinDigitalOutput pin3;
	final GpioPinDigitalOutput pin4;
	
	final GpioPinDigitalOutput[] pins;
	final GpioPinDigitalOutput[] selectPins;
*/
	boolean valve1 = false, valve2 = false, valve3 = false, valve4 = false, valve5 = false, valve6 = false, valve7 = false, valve8 = false;  
	boolean[] valveStates = {valve1, valve2, valve3, valve4, valve5, valve6, valve7, valve8};
	
	boolean pin1on = false, pin2on = false, pin3on = false, pin4on=false;
	boolean[] pinStates = {pin1on, pin2on, pin3on, pin4on};
	
	private Control cont;
	
	private final long TIME_PER_OZ = 1000;
	
	public HardwareControl(Control c){
		cont = c;		
/*
		gpio = GpioFactory.getInstance();
		pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "selLow", PinState.LOW);
		pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "selMid", PinState.LOW);
		pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "selHigh", PinState.LOW);
		pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "enablePin", PinState.LOW);
		
		pins = new GpioPinDigitalOutput[] {pin1, pin2, pin3, pin4};
		selectPins = new GpioPinDigitalOutput[] {pin3, pin2, pin1};
*/
		
	}
	
	
	public void pourValve(int valveId, double amount){
		long time = (long) amount*TIME_PER_OZ;
		if(valveId > 7 || valveId < 0)
			return;
		String binaryIndex = Integer.toBinaryString(valveId);
		while (binaryIndex.length() <3){ binaryIndex = "0"+binaryIndex;}
		System.out.println("binary: " + binaryIndex);
		for(int i = 0; i< 3; i++){
//			selectPins[i].setState(binaryIndex.charAt(i)=='1');
			pinStates[i] = (binaryIndex.charAt(i)=='1');
		}
		pinStates[3]=true;
		System.out.println(valveId +" = " + stateToString(pinStates));
//		pin4.pulse(time,true);
		pinStates[3]=false;
		System.out.println(valveId +" = " + stateToString(pinStates));
		clearPinStates();
	}
	
	public void clearPinStates(){
/*
 		for(GpioPinDigitalOutput pin : pins){
			pin.setState(false);
		}
*/

		for (int i = 0; i < pinStates.length; i++) {
			pinStates[i] = false;
		}
	}
	
	public void makeRecipe(Recipe r){
		List<Instruction> instr = r.getInstructions();
		for(Instruction i : instr){
			pourValve(valveMap(i.getIngredient()), i.getQuantity());
		}
	}
	
	private String stateToString(boolean[] states){
		String res = "[ ";
		for(boolean s : states){
			if(s)
				res+="1 ";
			else
				res+="0 ";
		}
		res+="]";
		return res;
	}
	
	private int valveMap(Ingredient i){
		return cont.bucketIds.indexOf(i.Id);
	}
	
}
