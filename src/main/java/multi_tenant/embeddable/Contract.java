package multi_tenant.embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Contract implements Serializable {

	private static final long serialVersionUID = -1442927146616879394L;

	@NotBlank(message = "Número do contrato é obrigatório.")
	@Size(min = 3, max = 60)
	@Column(name = "number_contract", length = 60, unique = true)
	private String numberContract;

	@Column(name = "start_contract")
	private LocalDate startDate;

	@Column(name = "final_contract")
	private LocalDate finalDate;

	private Boolean situation = true;

	public Contract() {
		// TODO Auto-generated constructor stub
	}

	public String getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}

	public Boolean getSituation() {
		return situation;
	}

	public void setSituation(Boolean situation) {
		this.situation = situation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numberContract);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(numberContract, other.numberContract);
	}

}
