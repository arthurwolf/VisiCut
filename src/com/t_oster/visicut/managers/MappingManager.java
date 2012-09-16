/**
 * This file is part of VisiCut.
 * Copyright (C) 2011 Thomas Oster <thomas.oster@rwth-aachen.de>
 * RWTH Aachen University - 52062 Aachen, Germany
 * 
 *     VisiCut is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *    VisiCut is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with VisiCut.  If not, see <http://www.gnu.org/licenses/>.
 **/
package com.t_oster.visicut.managers;

import com.t_oster.visicut.misc.Helper;
import com.t_oster.visicut.model.mapping.MappingSet;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Oster <thomas.oster@rwth-aachen.de>
 */
public class MappingManager
{

  private static MappingManager instance;
  
  public static MappingManager getInstance()
  {
    if (instance == null)
    {
      instance = new MappingManager();
    }
    return instance;
  }
  
  /**
   * Need public constructor for UI Editor.
   * Do not use. Use getInstance instead
   */
  public MappingManager()
  {
    if (instance != null)
    {
      System.err.println("Should not directly instanctiate MappingManager");
    }
  }

  private List<MappingSet> loadFromDirectory(File dir)
  {
    List<MappingSet> result = new LinkedList<MappingSet>();
    if (dir.isDirectory())
    {
      for (File f : dir.listFiles())
      {
        if (f.isFile() && f.getAbsolutePath().toLowerCase().endsWith(".xml"))
        {
          try
          {
            MappingSet s = this.loadMappingSet(f);
            result.add(s);
          }
          catch (Exception ex)
          {
            Logger.getLogger(MappingManager.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
    Collections.sort(result, new Comparator<MappingSet>(){

      public int compare(MappingSet t, MappingSet t1)
      {
        return t.getName().compareTo(t1.getName());
      }
    });
    return result;
  }

  protected List<MappingSet> mappingSets = null;
  public static final String PROP_MAPPINGSETS = "mappingSets";

  /**
   * Get the value of mappingSets
   *
   * @return the value of mappingSets
   */
  public List<MappingSet> getMappingSets()
  {
    if (mappingSets == null)
    {
      mappingSets = this.loadFromDirectory(this.getMappingsDirectory());
    }
    return mappingSets;
  }

  /**
   * Set the value of mappingSets
   *
   * @param mappingSets new value of mappingSets
   */
  public void setMappingSets(List<MappingSet> mappingSets) throws FileNotFoundException
  {
    for (MappingSet mp:this.getMappingSets().toArray(new MappingSet[0]))
    {
      this.removeMappingSet(mp);
    }
    for (MappingSet mp:mappingSets)
    {
      this.addMappingSet(mp);
    }
  }
  
  private File getMappingsDirectory()
  {
    return new File(Helper.getBasePath(), "mappings");
  }
  
  private File getMappingSetPath(MappingSet ms)
  {
    return new File(getMappingsDirectory(), Helper.toPathName(ms.getName())+".xml");
  }
  
  public void addMappingSet(MappingSet pref) throws FileNotFoundException
  {
    this.getMappingSets().add(pref);
    saveMappingSet(pref, this.getMappingSetPath(pref));
  }

  public void saveMappingSet(MappingSet pref, File f) throws FileNotFoundException
  {
    FileOutputStream os = new FileOutputStream(f);
    XMLEncoder encoder = new XMLEncoder(os);
    encoder.writeObject(pref);
    encoder.close();
  }

  public MappingSet loadMappingSet(File f) throws FileNotFoundException, IOException
  {
    FileInputStream in = new FileInputStream(f);
    MappingSet p = this.loadMappingSet(in);
    in.close();
    return p;
  }

  public MappingSet loadMappingSet(InputStream in) throws FileNotFoundException
  {
    XMLDecoder decoder = new XMLDecoder(in);
    MappingSet p = (MappingSet) decoder.readObject();
    return p;
  }

  private void deleteMappingSet(MappingSet mp)
  {
    File f = this.getMappingSetPath(mp);
    if (f.exists())
    {
      f.delete();
    }
  }
  
  private void removeMappingSet(MappingSet mp)
  {
    this.deleteMappingSet(mp);
    this.getMappingSets().remove(mp);
  }

}
