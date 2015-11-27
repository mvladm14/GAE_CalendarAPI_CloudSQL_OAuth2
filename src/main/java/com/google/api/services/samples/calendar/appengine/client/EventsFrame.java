package com.google.api.services.samples.calendar.appengine.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Vlad@google.com (Your Name Here)
 *
 */
public class EventsFrame extends Composite {
  
  interface MyUiBinder extends UiBinder<VerticalPanel, EventsFrame> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  FlexTable eventsTable;

  final CalendarGwtSample main;

  public EventsFrame(CalendarGwtSample main) {
    this.main = main;
    initWidget(uiBinder.createAndBindUi(this));
  }
}
