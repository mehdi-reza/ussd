/*
 *  (c) 2016. SMSGH
 */
package com.kashegypt.ussd.demo.controllers;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kashegypt.ussd.framework.UssdController;
import com.kashegypt.ussd.framework.UssdForm;
import com.kashegypt.ussd.framework.UssdInput;
import com.kashegypt.ussd.framework.UssdMenu;
import com.kashegypt.ussd.framework.UssdMenuItem;
import com.kashegypt.ussd.framework.UssdResponse;

/**
 *
 * @author Aaron Baffour-Awuah
 */
public class MainController extends UssdController {
    
	private Map<String,String> usersList = new HashMap<>();
	
	public MainController() {
		usersList.put("9040634328971", "Mehdi Raza");
		usersList.put("9040634328972", "Sumair Farooqi");
		usersList.put("9040634328973", "Hussain Itiba");
		usersList.put("9040634328974", "Hasnat Saeed");
	}
    public UssdResponse start() {
        UssdMenu menu = new UssdMenu().header(String.format("Welcome %s", "Mehdi"))
                .addItem("Greet me", "greetingForm")
                .addItem("What's the time?", "time")
                .addItem(new UssdMenuItem("0", "Exit", "exit"))
                .footer("\nPowered by SMSGH");
        return renderMenu(menu);
    }
    
    public UssdResponse greetingForm() {
        String formHeader = "Greet Me!";
        UssdForm form = new UssdForm("greeting")
                .addInput(new UssdInput("Name").header(formHeader))
                .addInput(new UssdInput("City").header(formHeader)
                        .addOption(new UssdInput.Option("Karachi", "K"))
                        .addOption(new UssdInput.Option("Lahore", "L")));
        return renderForm(form);
    }
    
    public UssdResponse greeting() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting = "";
        if (hour < 12) {
            greeting = "Good morning";
        }
        if (hour >= 12) {
            greeting = "Good afternoon";
        }
        if (hour >= 16) {
            greeting = "Good evening";
        }
        if (hour >= 21) {
            greeting = "Good night";
        }        
        
        Map<String, String> formData = getFormData();
        
        String name = formData.get("Name");
        String cityCode = formData.get("City");
        String city = cityCode.equals("K") ? "Karachi" : "Lahore";
        return render(String.format("%s %s, Welcome from %s", greeting, name, city));
    }
    
    public UssdResponse time() {
        return render(DateFormat.getTimeInstance(DateFormat.SHORT)
                .format(new Date()));
    }
    
    public UssdResponse exit() {
        return render("Bye bye!");
    }
}
