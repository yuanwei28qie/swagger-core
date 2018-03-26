package io.swagger.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;

public class MyClass extends MySuperClass {

    public void populate(String isotonicDrink, String softDrink) {
        this.isotonicDrink = isotonicDrink;
        this.softDrink = softDrink;
    }

    @XmlElement(name = "beerDrink")
    //@ApiModelProperty(name = "beerDrink")
    //@JsonProperty("beerDrink")
    private String isotonicDrink;

    @XmlElement(name = "sugarDrink")
    private String softDrink;
}
