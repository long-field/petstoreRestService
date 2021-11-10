package be.dietervanlangenaker.petstore.domain;

import com.mysql.cj.xdevapi.Collection;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String name;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoryid",referencedColumnName = "id")
    private Category category;

    private String photourls;
    @ElementCollection
    @JoinTable(name="tags", joinColumns=@JoinColumn(name="petid",referencedColumnName = "id"))
    private Set<Tag> tags;
    @Enumerated(EnumType.STRING)
    private Status status;



    public Pet(String name, Category category, String photourls, Status status, Set<Tag> tags) {
        this.name = name;
        this.category = category;
        this.photourls = photourls;
        this.tags = tags;
        this.status = status;

    }

    protected Pet() {

    }

    public Pet(long id, String name, Category category, String photourls, Status status, Set<Tag> tags) {
        this.id=id;
        this.name = name;
        this.category = category;
        this.photourls = photourls;
        this.tags = tags;
        this.status = status;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPhotourls() {
        return photourls;
    }


    public Set<Tag> getTags() {
        return tags;
    }


    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    public void setPhotourls(String photourls) {
        this.photourls = photourls;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Pet withId(long id) {
        var petWithId = new Pet(name, category, photourls, status, tags); petWithId.id = id;
        return petWithId; }

}
