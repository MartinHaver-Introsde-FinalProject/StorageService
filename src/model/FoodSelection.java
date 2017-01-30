package model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "foodSelection")
@XmlType(propOrder = {"idFoodSelection", "label", "image", "calories", "weight", "ingredients", "current"})
@XmlAccessorType(XmlAccessType.FIELD)
public class FoodSelection implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "idFoodSelection")
	private int idFoodSelection;

	@XmlElement(name = "label")
	private String label;

	@XmlElement(name = "image")
	private String image;

	@XmlElement(name = "calories")
	private double calories;

	@XmlElement(name = "weight")
	private double weight;

	@XmlElement(name = "ingredients")
	private String ingredients;

	@XmlElement(name = "current")
	private int current;
	
	public FoodSelection() {
		
	}

	public int getIdFoodSelection() {
		return idFoodSelection;
	}

	public void setIdFoodSelection(int idFoodSelection) {
		this.idFoodSelection = idFoodSelection;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

}
