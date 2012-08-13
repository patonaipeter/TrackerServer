package at.ac.tuwien.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user",
	uniqueConstraints = {@UniqueConstraint(columnNames={"username"}), @UniqueConstraint(columnNames={"email"})}
)
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

//	@NotEmpty
//	@Length(min = 5, max = 20)
	@Column(name = "username")
	private String username;

//	@NotEmpty
	@Column(name = "password")
	private String password;

//	@NotEmpty
//	@Email
	@Column(name = "email", nullable=false)
	private String email;
	
	@Column(name = "score")
	private Integer score;

	
	//TODO
	//validation
	//list of friends
	//connection to race
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	
}
