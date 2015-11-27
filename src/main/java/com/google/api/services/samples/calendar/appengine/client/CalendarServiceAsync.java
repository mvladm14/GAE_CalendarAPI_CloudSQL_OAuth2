package com.google.api.services.samples.calendar.appengine.client;

import com.google.api.services.samples.calendar.appengine.shared.GwtCalendar;
import com.google.api.services.samples.calendar.appengine.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface CalendarServiceAsync {
  void getCalendars(AsyncCallback<List<GwtCalendar>> callback);

  void delete(GwtCalendar calendar, AsyncCallback<Void> callback);

  void insert(GwtCalendar calendar, AsyncCallback<GwtCalendar> callback);

  void update(GwtCalendar updated, AsyncCallback<GwtCalendar> callback);
  
  void getEvents(GwtCalendar calendar, AsyncCallback<List<GwtEvent>> callback);
}
