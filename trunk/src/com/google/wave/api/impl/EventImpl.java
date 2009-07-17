// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.Wavelet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The Event implementation that wraps an underlying EventData object and can
 * traverse wavelets and blips if available in the message bundle.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class EventImpl implements Event {
  
  RobotMessageBundleImpl events;
  private EventData eventData;

  public EventImpl(EventData eventData, RobotMessageBundleImpl events) {
    this.events = events;
    this.eventData = eventData;
  }

  @SuppressWarnings("unchecked")
  public Collection<String> getAddedParticipants() {
    return (ArrayList<String>) eventData.getProperties().get("participantsAdded");
  }

  @Override
  public Blip getBlip() {
    return new BlipImpl(events.getBlipData().get(eventData.getProperties().get("blipId")), events);
  }

  @Override
  public String getChangedTitle() {
    return getProperty("title");
  }

  private String getProperty(String property) {
    return (String) eventData.getProperties().get(property);
  }

  @Override
  public Long getChangedVersion() {
    return Long.parseLong(getProperty("version"));
  }

  @Override
  public String getCreatedBlipId() {
    return getProperty("createdBlipId");
  }

  @Override
  public String getModifiedBy() {
    return eventData.getModifiedBy();
  }

  @Override
  public String getRemovedBlipId() {
    return getProperty("removedBlipId");
  }

  @Override
  public Collection<String> getRemovedParticipants() {
    return unflattenParticipantList(getProperty("participantsRemoved"));
  }

  private Collection<String> unflattenParticipantList(String property) {
    return Arrays.asList(property.split(","));
  }

  @Override
  public Long getTimestamp() {
    return eventData.getTimestamp();
  }

  @Override
  public EventType getType() {
    return eventData.getType();
  }

  @Override
  public Wavelet getWavelet() {
    return events.getWavelet();
  }
  
  @Override
  public String getButtonName() {
    return getProperty("button");
  }
}
