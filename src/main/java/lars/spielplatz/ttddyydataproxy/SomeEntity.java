package lars.spielplatz.ttddyydataproxy;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SomeEntity {

    public SomeEntity() {
    }

    public SomeEntity(final int id) {
        setId(id);
        setCreatedDate(new Date());
    }

    @Id
    private Integer id;

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