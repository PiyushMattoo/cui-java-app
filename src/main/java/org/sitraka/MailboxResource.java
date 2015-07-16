package org.sitraka;

import javax.ws.rs.*;
import java.util.*;
import java.sql.*;

@Path("/mailboxes")
public class MailboxResource
{
  private Set<Mailbox> mailboxes;
  private DBConnection instance;

  public MailboxResource() {
    this.instance = DBConnection.getInstance();
  }

  public Object parseResultSet(Object res) {
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
  public Set<Mailbox> getMailboxes() {
    return (Set<Mailbox>) this.parseResultSet(this.instance.query("SELECT * FROM mailboxes ORDER BY mailboxId;"));
  }

  @POST
  @Path("/")
  @Produces("application/json")
  public Object createMailbox( @FormParam("migrationId") String migrationId,
                               @FormParam("email") String email,
                               @FormParam("emails") String emails,
                               @FormParam("contacts") String contacts,
                               @FormParam("calendars") String calendars,
                               @FormParam("memos") String memos) {
    String query = String.format("INSERT INTO mailboxes (migrationId, email, emails, contacts, calendars, memos) VALUES (%s, \"%s\", %s, %s, %s, %s)", migrationId, email, emails, contacts, calendars, memos);

    System.out.println("SQL Query: " + query);


    if (this.instance.update(query) != -1) {
      return 200;
    }
    return null;
  }

  @GET
  @Path("/{id}")
  @Produces("application/json")
  public Object getMailbox( @PathParam("id") int id)
  {
    Set<Mailbox> res = (Set<Mailbox>) this.parseResultSet(this.instance.query("SELECT * FROM mailboxes WHERE mailboxId = " + id));

    if (res.size() != 0) {
      Mailbox mailbox = res.iterator().next();
      return mailbox;
    }
    return null;
  }
}
