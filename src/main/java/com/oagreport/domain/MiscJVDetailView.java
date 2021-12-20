package com.oagreport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "vw_misc_jv_detail")
public class MiscJVDetailView {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "SNO")
	private Long sno;

	@NotNull
	@Size(max = 2)
	@Column(name = "TXN_TYPE")
	private String txnType; // Dr/Cr

	@Column(name = "programCode")
	private String programCode;

	@Column(name = "economicNDescription")
	private String economicNDescription;

	@Column(name = "headNDescription")
	private String headNDescription;

	@Column(name = "programName")
	private String programName;

	@Column(name = "donorNdescription")
	private String donorNdescription;

	@Column(name = "sourceTypeNdescription")
	private String sourceTypeNdescription;

	@Column(name = "DR_AMOUNT")
	private Double drAmount;

	@Column(name = "CR_AMOUNT")
	private Double crAmount;

	@Column(name = "JV_ID")
	private Long jvId;

	public MiscJVDetailView() {
	}

}