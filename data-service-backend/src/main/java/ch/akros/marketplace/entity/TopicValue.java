package ch.akros.marketplace.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
@Setter
@ToString
@Table(name = "TOPIC_VALUE")
public class TopicValue {
	@Id
	@Column(name = "TOPIC_VALUE_ID", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long topicValueId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "TOPIC_ID", name = "TOPIC_ID", foreignKey = @ForeignKey(name = "TOPIC_VALUE_TOPIC_FK"))
	@ToString.Exclude
	private Topic topic;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "THEME_ID", name = "THEME_ID", foreignKey = @ForeignKey(name = "TOPIC_VALUE_THEME_FK"))
	@ToString.Exclude
	private Theme theme;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "FIELD_TYPE_ID", name = "FIELD_TYPE_ID", foreignKey = @ForeignKey(name = "TOPIC_VALUE_FIELD_TYPE_FK"))
	@ToString.Exclude
	private FieldType fieldType;

	@Column(name = "VALID_FROM")
	private Date validFrom;

	@Column(name = "VALID_TO")
	private Date validTo;
}
