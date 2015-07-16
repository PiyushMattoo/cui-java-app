package org.sitraka;

import java.io.*;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import java.sql.*;

public class Migration implements java.io.Serializable {
  private int migrationId;
  private String name;
  private Set<Mailbox> mailboxes;


  public Migration() {

  }

  public static Migration migrationFromResultSet(ResultSet rs) throws Exception {
    return new Migration(rs.getInt("migrationId"), rs.getString("name"), new HashSet<Mailbox>());
  }

  public Migration(int migrationId, String name, Set<Mailbox> mailboxes) {
    this.migrationId = migrationId;
    this.name = name;
    this.mailboxes = mailboxes;
  }

  public int getMigrationId() {
    return this.migrationId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMailboxCount() {
    return this.mailboxes.size();
  }

  public Collection getMailboxIds() {
    Set<Object> ids = new HashSet<Object>();

    for (Mailbox m : this.mailboxes) {
      ids.add(m.getMailboxId());
    }

    return ids;
  }
}
