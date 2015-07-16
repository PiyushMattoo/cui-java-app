package org.sitraka;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.*;

import java.sql.*;

@Path("/migrations")
public class MigrationResource
{
  private DBConnection instance;

  public MigrationResource() {
    this.instance = DBConnection.getInstance();
  }

  public Object parseResultSet(Object res) {
    if (res == null) {
      return null;
    }
    ResultSet rs = (ResultSet) res;
    Set<Migration> migrations = new LinkedHashSet<Migration>();
    try {
      while (rs.next()) {
        migrations.add(Migration.migrationFromResultSet(rs));
      }
    } catch (Exception ex) {
      System.out.println("Exception");
      ex.printStackTrace();
    }
    finally {
      return migrations;
    }
  }

  public Object parseMailboxes(Object res) {
    if (res == null) {
      return null;
    }
    ResultSet rs = (ResultSet) res;
    Set<Mailbox> mailboxes = new LinkedHashSet<Mailbox>();
    try {
      while (rs.next()) {
        mailboxes.add(Mailbox.mailboxFromResultSet(rs));
      }
    } catch (Exception ex) {
      System.out.println("Exception");
      ex.printStackTrace();
    }
    finally {
      return mailboxes;
    }
  }

  @GET
  @Path("/")
  @Produces("application/json")
  public Set<Migration> getMigrations() {
     return (Set<Migration>) this.parseResultSet(this.instance.query("SELECT * FROM migrations ORDER BY migrationId;"));
  }

  @GET
  @Path("/{id}")
  @Produces("application/json")
  public Object getMigration( @PathParam("id") int id)
  {
    Set<Migration> res = (Set<Migration>) this.parseResultSet(this.instance.query("SELECT * FROM migrations WHERE migrationId = " + id));

    if (res.size() != 0) {
      Migration migration = res.iterator().next();
      return migration;
    }
    return null;
  }

  @GET
  @Path("/{id}/mailboxes")
  @Produces("application/json")
  public Object getMailboxes( @PathParam("id") int id) {
    String query = "SELECT * FROM mailboxes WHERE migrationId = " + id;
    Set<Mailbox> res = (Set<Mailbox>) this.parseMailboxes(this.instance.query(query));
    return res;
  }
}
