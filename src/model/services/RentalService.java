package model.services;

import entities.Invoice;
import entities.carRental;

public class RentalService {
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilianTaxServices taxService;

	public RentalService() {
	}
	public RentalService(Double pricePerDay, Double pricePerHour, BrazilianTaxServices taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}

	public void processInvoice(carRental car) {
		
		double hours = (double) (car.getFinish().getTime() - car.getStart().getTime()) / 1000 / 60 / 60;

		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		} else {
			basicPayment = Math.ceil(hours/24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		car.setInvoice(new Invoice(basicPayment, tax));
	}
}
