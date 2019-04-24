package unidue.ub.services.gateway.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Plain old java object holding the user with their password and salt. The
 * fields can be persisted.
 * 
 * @author Eike Spielberg
 * @version 1
 */
@Entity
@Table(name="app_user")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(unique=true)
	private String username = "";

	@JsonIgnore
	private String password = "";

	private String email = "";

	private String fullname = "";

	private boolean enabled = false;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() { }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * returns the email
	 * 
	 * @return email the email
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets the email
	 * 
	 * @param email
	 *            the email
	 */
	public void setUsername(String email) {
		this.username = email;
	}

	/**
	 * returns the password
	 *
	 * @return password the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * sets the password
	 * 
	 * @param password
	 *            the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}