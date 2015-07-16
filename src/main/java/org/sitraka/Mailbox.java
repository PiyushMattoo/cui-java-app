package org.sitraka;

import java.io.*;
import java.sql.*;

public class Mailbox implements java.io.Serializable {

  private int mailboxId;
  private String email;
  private Boolean emails;
  private Boolean calendars;
  private Boolean contacts;
  private Boolean memos;

  public Mailbox(int mailboxId, String email, Boolean emails, Boolean calendars, Boolean contacts, Boolean memos) {
    this.mailboxId = mailboxId;
    this.email = email;
    this.emails = emails;
    this.calendars = calendars;
    this.contacts = contacts;
    this.memos = memos;
  }

  public static Mailbox mailboxFromResultSet(ResultSet rs) throws Exception {
    return new Mailbox(rs.getInt("mailboxId"), rs.getString("email"), rs.getBoolean("emails"), rs.getBoolean("calendars"), rs.getBoolean("contacts"), rs.getBoolean("memos"));
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getMailboxId() {
    return this.mailboxId;
  }

  public Boolean getEmails() {
    return this.emails;
  }

  public Boolean getCalendars() {
    return this.calendars;
  }

  public Boolean getContacts() {
    return this.contacts;
  }

  public Boolean getMemos() {
    return this.memos;
  }
}
