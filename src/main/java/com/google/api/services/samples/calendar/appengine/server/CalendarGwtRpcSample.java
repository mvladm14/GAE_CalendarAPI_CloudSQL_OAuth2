package com.google.api.services.samples.calendar.appengine.server;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.samples.calendar.appengine.client.CalendarService;
import com.google.api.services.samples.calendar.appengine.shared.GwtCalendar;
import com.google.api.services.samples.calendar.appengine.shared.GwtEvent;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CalendarGwtRpcSample extends RemoteServiceServlet implements CalendarService {
  
  @Override
  public List<GwtCalendar> getCalendars() throws IOException {
    try {
      com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();
      com.google.api.services.calendar.Calendar.CalendarList.List listRequest =
          client.calendarList().list();
      listRequest.setFields("items(id,summary)");
      CalendarList feed = listRequest.execute();
      ArrayList<GwtCalendar> result = new ArrayList<GwtCalendar>();
      if (feed.getItems() != null) {
        for (CalendarListEntry entry : feed.getItems()) {
          result.add(new GwtCalendar(entry.getId(), entry.getSummary()));
        }
      }
      return result;
    } catch (IOException e) {
      throw Utils.wrappedIOException(e);
    }
  }

  @Override
  public void delete(GwtCalendar calendar) throws IOException {
    try {
      com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();
      client.calendars().delete(calendar.id).execute();
    } catch (IOException e) {
      throw Utils.wrappedIOException(e);
    }
  }

  @Override
  public GwtCalendar insert(GwtCalendar calendar) throws IOException {
    try {
      Calendar newCalendar = new Calendar().setSummary(calendar.title);
      com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();
      Calendar responseEntry = client.calendars().insert(newCalendar).execute();
      GwtCalendar result = new GwtCalendar();
      result.title = responseEntry.getSummary();
      result.id = responseEntry.getId();
      return result;
    } catch (IOException e) {
      throw Utils.wrappedIOException(e);
    }
  }

  @Override
  public GwtCalendar update(GwtCalendar updated) throws IOException {
    try {
      com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();
      Calendar entry = new Calendar();
      entry.setSummary(updated.title);
      String id = updated.id;
      Calendar responseEntry = client.calendars().patch(id, entry).execute();
      return new GwtCalendar(id, responseEntry.getSummary());
    } catch (IOException e) {
      throw Utils.wrappedIOException(e);
    }
  }

  @Override
  public List<GwtEvent> getEvents(GwtCalendar calendar) throws IOException {
    List<GwtEvent> gwtEvents = new ArrayList<GwtEvent>();
    try {
      com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();

      // Iterate over the events in the specified calendar
      String pageToken = null;
      do {
        Events events = client.events().list(calendar.id).setPageToken(pageToken).execute();
        List<Event> items = events.getItems();
        for (Event event : items) {
          gwtEvents.add(new GwtEvent(event.getId(), event.getSummary()));
        }
        pageToken = events.getNextPageToken();
      } while (pageToken != null);
    } catch (IOException e) {
      throw Utils.wrappedIOException(e);
    }

    Utils.deleteEntriesFromCloudSql();
    
    Utils.insertEntriesIntoCloudSql(gwtEvents);    

    gwtEvents.clear();
    
    gwtEvents.addAll(Utils.fetchEntriesFromCloudSql());    
    
    return gwtEvents;
  }

  
}
