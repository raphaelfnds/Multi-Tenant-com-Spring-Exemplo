package multi_tenant.auth.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import multi_tenant.embeddable.Contract;

@Table(schema = "public" ,name = "client", uniqueConstraints = @UniqueConstraint(name = "uk_number_contract", columnNames = "number_contract"))
@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 7337381997944909702L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome é obrigatório.")
	@Size(min = 3, max = 60)
	@Column(name = "client_name", length = 60, nullable = false)
	private String name;

	@NotBlank(message = "CPF ou CNPJ é obrigatório.")
	@Column(name = "cpf_cnpj", nullable = false, length = 15)
	private String cpfCnpj;

	@Embedded
	private Contract contract;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserClient> userList = new HashSet<>();

	public Client() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Set<UserClient> getUserList() {
		return userList;
	}

	public void setUserList(Set<UserClient> userList) {
		this.userList = userList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(id, other.id);
	}

}
