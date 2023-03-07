package org.mfp.softball.dcusssa;

import org.mfp.softball.dcusssa.data.Entry;

/**
 * Created by gawarho on 2/21/18.
 */
public interface EntryHandler {
    public void process(Entry e, String table);
}
