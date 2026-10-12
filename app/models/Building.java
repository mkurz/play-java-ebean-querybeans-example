package models;

import io.ebean.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Building extends Model {
  @Id public Long id;
  public String name;
}
