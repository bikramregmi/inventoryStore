package com.bikram.smart.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Program entity.
 */
public class ProgramDTO implements Serializable {

    private Long id;

    private String image;

    private String description;

    private String name;

    private Set<CourseDTO> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDTO> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramDTO programDTO = (ProgramDTO) o;
        if(programDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgramDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
