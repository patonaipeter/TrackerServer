package at.ac.tuwien.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

	private Date register_date;
	private Date last_activity_date;
	
	@OneToMany
	private List<User> friends;
	
	@ManyToMany(mappedBy = "participants")
	private Set<Race> races;
	
	
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

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public Date getLast_activity_date() {
		return last_activity_date;
	}

	public void setLast_activity_date(Date last_activity_date) {
		this.last_activity_date = last_activity_date;
	}

	public List<User> getFirends() {
		return friends;
	}

	public void setFirends(List<User> firends) {
		this.friends = firends;
	}

	public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}
	
	public void addFriend(User u){
		if(this.getFirends() != null){
			this.getFirends().add(u);
		}else{
			List<User> liste = new ArrayList<User>();
			liste.add(u);
			this.setFirends(liste);
		}
	}
	
	
}
