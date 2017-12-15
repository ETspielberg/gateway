package unidue.ub.services.gateway.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Plain old java object holding the user connected roles with the corresponding
 * email. The fields can be persisted.
 * 
 * @author Eike Spielberg
 * @version 1
 */
@Entity
public class Role {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique=true)
	@NotNull
	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Set<User> users;

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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
