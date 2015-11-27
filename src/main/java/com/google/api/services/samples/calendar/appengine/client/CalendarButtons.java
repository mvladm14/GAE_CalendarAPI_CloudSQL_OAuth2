package com.google.api.services.samples.calendar.appengine.client;

import com.google.api.services.samples.calendar.appengine.shared.AuthenticationException;
import com.google.api.services.samples.calendar.appengine.shared.GwtCalendar;
import com.google.api.services.samples.calendar.appengine.shared.GwtEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.List;

public class CalendarButtons extends Composite {
  interface MyUiBinder extends UiBinder<HorizontalPanel, CalendarButtons> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  Button deleteButton;

  @UiField
  Button updateButton;

  @UiField
  Button listEventsButton;

  private final int calendarIndex;

  private final CalendarGwtSample main;

  private final GwtCalendar calendar;

  EventsFrame eventsFrame;

  public CalendarButtons(CalendarGwtSample main, GwtCalendar calendar, int calendarIndex) {
    this.main = main;
    this.calendar = calendar;
    this.calendarIndex = calendarIndex;
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiHandler("deleteButton")
  void handleDelete(ClickEvent e) {
    DialogBox dialogBox = new DialogBox();
    dialogBox.setAnimationEnabled(true);
    dialogBox.setText("Are you sure you want to permanently delete the calendar?");
    DeleteDialogContent content = new DeleteDialogContent(main, dialogBox, calendar, calendarIndex);
    dialogBox.add(content);
    dialogBox.show();
  }

  @UiHandler("updateButton")
  void handleUpdate(ClickEvent e) {
    DialogBox dialogBox = new DialogBox();
    dialogBox.setAnimationEnabled(true);
    dialogBox.setText("Update Calendar Title:");
    UpdateDialogContent content = new UpdateDialogContent(main, dialogBox, calendar, calendarIndex);
    dialogBox.add(content);
    dialogBox.show();
  }

  @UiHandler("listEventsButton")
  void handleListEvents(ClickEvent e) {
    RootPanel.get("main_events").clear();
    eventsFrame = new EventsFrame(main);
    RootPanel.get("main_events").add(eventsFrame);
    FlexTable eventsTable = eventsFrame.eventsTable;
    eventsTable.setText(0, 0, "Loading Events From Google Calendar to Cloud SQL and from Cloud SQL back to appengine app.");
    eventsTable.getCellFormatter().addStyleName(0, 0, "methodsHeaderRow");

    CalendarGwtSample.SERVICE.getEvents(calendar, new AsyncCallback<List<GwtEvent>>() {

      @Override
      public void onFailure(Throwable caught) {
        handleFailure(caught);
      }

      @Override
      public void onSuccess(List<GwtEvent> result) {
        refreshEventsTable(result);
      }
    });
  }

  /**
   * @param result
   */
  void refreshEventsTable(List<GwtEvent> result) {

    FlexTable eventsTable = eventsFrame.eventsTable;
    eventsTable.setText(0, 0, "Events");
    eventsTable.getCellFormatter().addStyleName(0, 0, "methodsHeaderRow");

    // List all the rows.
    for (int i = 0; i < result.size(); i++) {
      GwtEvent event = result.get(i);
      eventsTable.setText(i + 1, 1, event.title);
    }
  }

  static void handleFailure(Throwable caught) {
    if (caught instanceof AuthenticationException) {
      Window.Location.reload();
    } else {
      caught.printStackTrace();
      Window.alert("ERROR: " + caught.getMessage());
    }
  }
}
