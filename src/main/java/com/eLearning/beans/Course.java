package com.eLearning.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="course")
public class Course {
	
	/*For id field, generating a uuid using
	 hibernate using uuid2 strategy*/
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
    private String id;
	
	private String title;
	
	private String slug;
	
	private String description;
	
	private String time;
	
	/* One to one mapping to graphic table 
	 	by joining through id field.*/
	
	@OneToOne(fetch = FetchType.EAGER)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE})
	private Graphic graphic;
	
	private String language;
	
	private String skill;
	
	private String date_created;
	
	private String last_update;
	
	private int lessons_count;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSlug() {
		return slug;
	}
	
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getSkill() {
		return skill;
	}
	
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public String getDate_created() {
		return date_created;
	}
	
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	
	public String getLast_update() {
		return last_update;
	}
	
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	
	public int getLessons_count() {
		return lessons_count;
	}
	
	public void setLessons_count(int lessons_count) {
		this.lessons_count = lessons_count;
	}
	
}
