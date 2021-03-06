package liquibase.changelog.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import liquibase.Contexts;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.sql.visitor.AbstractSqlVisitor;

public class ContextChangeSetFilterTest {


    private static final class TestSqlVisitor extends AbstractSqlVisitor {

        public TestSqlVisitor(final String... contexts) {
            setContexts(new Contexts(contexts));
        }

        @Override
        public String modifySql(String sql, Database database) {
            throw new UnsupportedOperationException("modifySql has not been implemented");
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("getName has not been implemented");
        }

    }


    @Test
    public void emptyContexts() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter();

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }

    @Test
    public void nullContexts() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter();

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }

    @Test
    public void nullListContexts() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter();

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }

    @Test
    public void singleContexts() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("TEST1");

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertFalse(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }

    @Test
    public void multiContexts() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("test1", "test2");

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertFalse(filter.accepts(new ChangeSet(null, null, false, false, null, "test3", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test3, test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test3, TEST1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }

    @Test
    public void multiContextsSingeParameter() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("test1, test2");

        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test2", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test1, test2", null, null)));
        assertFalse(filter.accepts(new ChangeSet(null, null, false, false, null, "test3", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test3, test1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, "test3, TEST1", null, null)));
        assertTrue(filter.accepts(new ChangeSet(null, null, false, false, null, null, null, null)));
    }


    @Test
    public void visitorContextFilterLowerLower() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("test1");

        ChangeSet changeSet = new ChangeSet(null, null, false, false, null, null, null, null);
        changeSet.addSqlVisitor(new TestSqlVisitor("test1"));

        assertTrue(filter.accepts(changeSet));

        assertEquals(1, changeSet.getSqlVisitors().size());
    }

    @Test
    public void visitorContextFilterUpperLower() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("TEST1");

        ChangeSet changeSet = new ChangeSet(null, null, false, false, null, null, null, null);
        changeSet.addSqlVisitor(new TestSqlVisitor("test1"));

        assertTrue(filter.accepts(changeSet));

        assertEquals(1, changeSet.getSqlVisitors().size());
    }

    @Test
    public void visitorContextFilterUpperUpper() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("TEST1");

        ChangeSet changeSet = new ChangeSet(null, null, false, false, null, null, null, null);
        changeSet.addSqlVisitor(new TestSqlVisitor("TEST1"));

        assertTrue(filter.accepts(changeSet));

        assertEquals(1, changeSet.getSqlVisitors().size());
    }

    @Test
    public void visitorContextFilterLowerUpper() {
        ContextChangeSetFilter filter = new ContextChangeSetFilter("test1");

        ChangeSet changeSet = new ChangeSet(null, null, false, false, null, null, null, null);
        changeSet.addSqlVisitor(new TestSqlVisitor("TEST1"));

        assertTrue(filter.accepts(changeSet));

        assertEquals(1, changeSet.getSqlVisitors().size());
    }
}
