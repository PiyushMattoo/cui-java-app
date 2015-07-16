/*
 * DELL PROPRIETARY INFORMATION
 *
 * This software is confidential.  Dell Inc., or one of its subsidiaries, has
 * supplied this software to you under the terms of a license agreement,
 * nondisclosure agreement or both.  You may not copy, disclose, or use this
 * software except in accordance with those terms.
 *
 * Copyright 2014 Dell Inc.
 * ALL RIGHTS RESERVED.
 *
 * DELL INC. MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. DELL SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */

package org.sitraka;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MigrationApplication extends Application {

  private Set<Object> singletons = new HashSet<Object>();
  private Set<Class<?>> empty = new HashSet<Class<?>>();

  public MigrationApplication()
  {
    singletons.add(new MailboxResource());
    singletons.add(new MigrationResource());
  }

   @Override
   public Set<Class<?>> getClasses()
   {
      return empty;
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }

}
