package com.mustahsan.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "user_info")
@JsonInclude(Include.NON_NULL)
public class UserInfo extends BaseModel {

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "photo", columnDefinition="LONGBLOB")
	private String photo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonIgnore
	private User user;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "gender", length = 32)
	private String gender;

	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "dob", length = 32)
	private Date dob;

	@JsonIgnore
	public Long getId(){
		return super.getId();
	}

}
