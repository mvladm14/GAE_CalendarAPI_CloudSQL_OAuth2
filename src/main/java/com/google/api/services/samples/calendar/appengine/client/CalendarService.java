package com.google.api.services.samples.calendar.appengine.client;

import com.google.api.services.samples.calendar.appengine.shared.GwtCalendar;
import com.google.api.services.samples.calendar.appengine.shared.GwtEvent;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.io.IOException;
import java.util.List;

@RemoteServiceRelativePath("calendarService")
public interface CalendarService extends RemoteService {

  List<GwtCalendar> getCalendars() throws IOException;

  void delete(GwtCalendar calendar) throws IOException;

  GwtCalendar insert(GwtCalendar calendar) throws IOException;

  GwtCalendar update(GwtCalendar updated) throws IOException;
  
  List<GwtEvent> getEvents(GwtCalendar calendar) throws IOException;
}
