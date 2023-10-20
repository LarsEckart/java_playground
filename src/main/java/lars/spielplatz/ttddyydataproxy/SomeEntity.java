package lars.spielplatz.ttddyydataproxy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class SomeEntity {

  public SomeEntity() {}

  public SomeEntity(final int id) {
    setId(id);
    setCreatedDate(new Date());
  }

  @Id private Integer id;

  private Date createdDate;

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
