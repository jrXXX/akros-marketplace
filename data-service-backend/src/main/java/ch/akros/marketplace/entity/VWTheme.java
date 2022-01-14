
package ch.akros.marketplace.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "VW_THEME")
public class VWTheme {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "THEME_ID")
  private Long   themeId;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "SEARCH_COUNT")
  private int    searchCount;

  @Column(name = "OFFER_COUNT")
  private int    offerCount;
}
