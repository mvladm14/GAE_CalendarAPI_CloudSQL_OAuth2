package com.google.api.services.samples.calendar.appengine.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vlad@google.com (Your Name Here)
 *
 */
@Entity
@Table(name = "GwtEvent")
@SuppressWarnings("serial")
public class GwtEvent implements Serializable {

  public GwtEvent() {
  }

  public GwtEvent(String id, String title) {
    this.id = id;
    this.title = title;
  }

  @Id
  public String id;
  public String title;
}
