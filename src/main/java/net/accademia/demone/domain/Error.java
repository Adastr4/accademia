package net.accademia.demone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity Error
 */
@ApiModel(description = "Entity Error")
@Entity
@Table(name = "error")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "error")
public class Error implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "errorid")
    private Integer errorid;

    @Column(name = "description")
    private String description;

    @Column(name = "data")
    private Instant data;

    @ManyToOne
    @JsonIgnoreProperties(value = { "errors" }, allowSetters = true)
    private Source source;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Error id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getErrorid() {
        return this.errorid;
    }

    public Error errorid(Integer errorid) {
        this.setErrorid(errorid);
        return this;
    }

    public void setErrorid(Integer errorid) {
        this.errorid = errorid;
    }

    public String getDescription() {
        return this.description;
    }

    public Error description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getData() {
        return this.data;
    }

    public Error data(Instant data) {
        this.setData(data);
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Error source(Source source) {
        this.setSource(source);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Error)) {
            return false;
        }
        return id != null && id.equals(((Error) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Error{" +
            "id=" + getId() +
            ", errorid=" + getErrorid() +
            ", description='" + getDescription() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
