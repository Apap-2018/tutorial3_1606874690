package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "brand", required = true) String brand,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "price", required = true) Long price,
			@RequestParam(value = "amount", required = true) Integer amount) {
		
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
	}
	
	/*@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		
		model.addAttribute("car", archive);
		return "view-car";
	}*/

	@RequestMapping("/car/viewall")
	public String viewall (Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";	
	}
	
	@RequestMapping(value = {"/car/view","/car/view/{id}"})
	public String viewID(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			
			if (archive != null) {
				model.addAttribute("car", archive);
				return "view-car";
				
			} else {
				return "view-error";
			}
		}
		
		return "view-error";
	}
	
	@RequestMapping(value = {"/car/update","/car/update/{id}/amount/{amount}"}) 
	public String update(@PathVariable Optional<String> id, @PathVariable Optional<String> amount, Model model) {
		if (id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			
			if (archive != null) {
				archive.setAmount(Integer.parseInt(amount.get()));
				model.addAttribute("car", archive);
				return "view-car";
				
			} else {
				return "view-error";
			}
			
		} else {
			return "view-error";
		}
	}
	
	@RequestMapping(value = {"/car/delete","/car/delete/{id}"}) 
	public String delete(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			
			if (archive != null) {
				for (int i = 0; i < mobilService.getCarList().size(); i++) {
					if (archive.equals(mobilService.getCarList().get(i))) {
						int idx = mobilService.getCarList().indexOf(archive);
						mobilService.getCarList().remove(idx);
						break;
					}
					
					
				}
				
				return "delete-car";
				
			} else {
				return "view-error";
			}
		}
		
		return "view-error";
	}
	
	
}


