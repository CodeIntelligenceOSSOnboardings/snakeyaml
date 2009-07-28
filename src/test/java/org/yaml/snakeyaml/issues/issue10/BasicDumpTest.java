/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml.issues.issue10;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.yaml.snakeyaml.JavaBeanDumper;
import org.yaml.snakeyaml.Util;
import org.yaml.snakeyaml.Yaml;

public class BasicDumpTest extends TestCase {

    public void testTag() {
        DataSource base = new DataSource();
        JDBCDataSource baseJDBC = new JDBCDataSource();
        baseJDBC.setParent(base);

        ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
        // trying expected order first
        dataSources.add(base);
        dataSources.add(baseJDBC);

        DataSources ds = new DataSources();
        ds.setDataSources(dataSources);

        Yaml yaml = new Yaml();
        String output = yaml.dump(ds);

        String etalon = Util.getLocalResource("javabeans/issue10-1.yaml");
        assertEquals(etalon.trim(), output.trim());
        Object obj = yaml.load(output);
        DataSources dsOut = (DataSources) obj;
        Iterator<DataSource> iter = dsOut.getDataSources().iterator();
        assertFalse("Must be DataSource.", iter.next() instanceof JDBCDataSource);
        assertTrue(iter.next() instanceof JDBCDataSource);
    }

    public void testTag2() {
        DataSource base = new DataSource();
        JDBCDataSource baseJDBC = new JDBCDataSource();
        baseJDBC.setParent(base);

        ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
        // trying expected order first
        dataSources.add(base);
        dataSources.add(baseJDBC);

        DataSources ds = new DataSources();
        ds.setDataSources(dataSources);

        JavaBeanDumper yaml = new JavaBeanDumper();
        String output = yaml.dump(ds);

        String etalon = Util.getLocalResource("javabeans/issue10-2.yaml");
        assertEquals(etalon.trim(), output.trim());
    }

    public void testTag3() {
        DataSource base = new DataSource();
        JDBCDataSource baseJDBC = new JDBCDataSource();
        baseJDBC.setParent(base);

        ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
        // trying expected order first
        dataSources.add(baseJDBC);
        dataSources.add(base);

        DataSources ds = new DataSources();
        ds.setDataSources(dataSources);

        JavaBeanDumper yaml = new JavaBeanDumper();
        String output = yaml.dump(ds);

        String etalon = Util.getLocalResource("javabeans/issue10-3.yaml");
        assertEquals(etalon.trim(), output.trim());
    }
}