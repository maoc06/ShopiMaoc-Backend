package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaymentMethodDTO {
	/*@NotNull
	@Positive*/
	private Integer payId;
	
	@NotNull
	@Size(min = 1, max = 1)
	@NotEmpty
	private String enable;
	
	@NotNull
	@Size(min = 1, max = 255)
	@NotEmpty
	private String name;
	
	@NotNull
	@Size(min = 1, max = 255)
	@NotEmpty
	private String image;

	public PaymentMethodDTO() {
		super();
	}

	public PaymentMethodDTO(Integer payId,String enable, String name, String image) {
		super();
		this.payId = payId;
		this.enable = enable;
		this.name = name;
		this.image = image;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}
