package net.accademia.demone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import net.accademia.demone.domain.enumeration.Fonte;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity name
 */
@ApiModel(description = "Entity name")
@Entity
@Table(name = "source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "source")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sourceid")
    private String sourceid;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fonte")
    private Fonte fonte;

    @Column(name = "data")
    private Instant data;

    @OneToMany(mappedBy = "source")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "source" }, allowSetters = true)
    private Set<Error> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Source id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceid() {
        return this.sourceid;
    }

    public Source sourceid(String sourceid) {
        this.setSourceid(sourceid);
        return this;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getDescription() {
        return this.description;
    }

    public Source description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fonte getFonte() {
        return this.fonte;
    }

    public Source fonte(Fonte fonte) {
        this.setFonte(fonte);
        return this;
    }

    public void setFonte(Fonte fonte) {
        this.fonte = fonte;
    }

    public Instant getData() {
        return this.data;
    }

    public Source data(Instant data) {
        this.setData(data);
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Set<Error> getErrors() {
        return this.errors;
    }

    public void setErrors(Set<Error> errors) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setSource(null));
        }
        if (errors != null) {
            errors.forEach(i -> i.setSource(this));
        }
        this.errors = errors;
    }

    public Source errors(Set<Error> errors) {
        this.setErrors(errors);
        return this;
    }

    public Source addError(Error error) {
        this.errors.add(error);
        error.setSource(this);
        return this;
    }

    public Source removeError(Error error) {
        this.errors.remove(error);
        error.setSource(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Source)) {
            return false;
        }
        return id != null && id.equals(((Source) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Source{" +
            "id=" + getId() +
            ", sourceid='" + getSourceid() + "'" +
            ", description='" + getDescription() + "'" +
            ", fonte='" + getFonte() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
