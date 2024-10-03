package multi_tenant.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import multi_tenant.embeddable.Contract;
import multi_tenant.enun.AcessType;

@Table(schema = "public", name = "client_user")
@Entity
public class UserClient implements Serializable {

	private static final long serialVersionUID = -3392191371121072619L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome de usuário é um campo obrigatório.")
	@Column(name = "nick_name", length = 65, nullable = false, unique = true)
	private String nickname;

	@NotBlank(message = "Nome de usuário é um campo obrigatório.")
	@Column(name = "user_name", length = 65, nullable = false)
	private String nameUser;

	@NotBlank(message = "CPF é um campo obrigatório.")
	@Column(length = 15, nullable = false)
	private String cpf;

	@NotBlank(message = "Email é um campo obrigatório.")
	@Column(name = "email", length = 205, unique = true, nullable = false)
	private String email;

	@Column(length = 555)
	private String password;

	@Enumerated(EnumType.STRING)
	private AcessType acessType;
	
	private String loja;

	private Boolean situation = true;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	public UserClient() {
		// TODO Auto-generated constructor stub
	}

	public Contract getContract() {
		return client != null ? client.getContract() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AcessType getAcessType() {
		return acessType;
	}

	public void setAcessType(AcessType acessType) {
		this.acessType = acessType;
	}

	public Boolean getSituation() {
		return situation;
	}

	public void setSituation(Boolean situation) {
		this.situation = situation;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
	    if (loja == null || loja.trim().isEmpty()) {
	        this.loja = "Não informado";
	    } else {
	        this.loja = loja;
	    }
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
		UserClient other = (UserClient) obj;
		return Objects.equals(id, other.id);
	}

}
